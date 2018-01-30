package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.security.auth.Destroyable;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dominio.User.StateUser;
import edu.uclm.esi.tysweb.laoca.websockets.WebSocketManager;


public class Sala {

	private String nameSala;
	private ConcurrentHashMap<Integer, User> players;
	private int playersToBeReady;
	private int jugadorConElTurno;
	private Tablero tablero;

	public Sala(User creador,String nameSala) {
		this.players = new ConcurrentHashMap<>();
		players.put(1,creador);
		this.playersToBeReady=2;
		this.tablero=new Tablero();
		this.nameSala = nameSala;
		creador.setState(StateUser.INSIDE_SALA);
	}

	public void add(User user) throws Exception {
		if(players.size() >= playersToBeReady) {
			throw new Exception("Sala ya llena");
		}
		user.setState(StateUser.INSIDE_SALA);
		players.put((players.size()+1),user);
	}
	public String getName() {
		return nameSala;
	}

	public boolean isCompleted() {
		return this.players.size()==this.playersToBeReady;
	}
	public boolean isReady() {
		if(!isCompleted()) return false;
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			if(user.getState() != StateUser.READY) return false;
		}
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			WebSocketManager.send(user, null, "START_GAME");
			user.setState(StateUser.PLAYING);
		}
		StartGame();
		return true;
		
	}

	public void StartGame() {
		sendInfoGame();
		sendRolGame();
		this.jugadorConElTurno=1;
		setTurnoAndSend(this.jugadorConElTurno);
	}
	private void setTurnoAndSend(int player) {
		System.out.println(player+"JUGADOR CON TURNO");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("NO PONER SLEEP AQUI");
			e.printStackTrace();
		}
		this.jugadorConElTurno=player;
		User user=players.get(jugadorConElTurno);
		if(user.getTurnosSinTirar() > 1){ //Si esta penalizado pasa turno al siguiente
			user.setTurnosSinTirar(user.getTurnosSinTirar()-1);
			sendMsg(user.getNick()+" tiene que estar sin tirar "+user.getTurnosSinTirar());	
			nextTurno();
		}else { //Envia el turno del jugador que le toca
			JSONObject jso=new JSONObject();
			jso.put("player", this.jugadorConElTurno);
			jso.put("name", players.get(player).getNick());
			sendBroadcastSala(jso, "TURNO_GAME");
			//TODO AQUI IRIA EL TIMEOUT
			//WebSocketManager.get().setTimeoutUser(user, "Lanza dado antes de 10 segundos o te echo");
		}
	}
	
	private void sendInfoGame() {
		//{"data":{"players":[{"player":1,"name":"diego"},{"player":2,"name":"pepe"}]},"type":"INFO_GAME"}
		JSONObject jso=new JSONObject();
		int i=1;
		JSONArray players = new JSONArray();
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			JSONObject player_data = new JSONObject();
			player_data.put("name", user.getNick());
			player_data.put("player",j);
			players.put(player_data);
		}
		jso.put("players", players);
		sendBroadcastSala(jso, "INFO_GAME");
	}
	private void sendRolGame() {
		for(int j=1;j<=this.players.size();j++) {
			User user =this.players.get(j);
			JSONObject jso=new JSONObject();
			jso.put("rol", j);
			WebSocketManager.send(user, jso, "ROL_GAME");
		}
	}
	
	private void sendBroadcastSala(JSONObject data,String type) {
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			WebSocketManager.send(user, data, type);
		}
	}
		
	public User getJugadorConElTurno() {
		if (this.players.size()==0)
			return null;
		return this.players.get(this.jugadorConElTurno);
	}

	public void tirarDado(User user, int dado) throws Exception {
		if (user!=getJugadorConElTurno()) //Si no tiene el turno devuelve error
			throw new Exception("No tienes el turno");
		Thread.sleep(2000); //Espera de 1 segundo para que pueda aparecer bien la informaci�n en todos los tableros
		Casilla destino=this.tablero.tirarDado(user, dado);
		Casilla siguiente=destino.getSiguiente();	
		this.tablero.moverAJugador(user, destino); //Mueve al jugador
		moveUser(user);
		sendMsg(user.getNick()+" mueve a casilla "+user.getCasilla().getPos());
		Thread.sleep(2000);
		if (siguiente!=null) {  //Si ha caido en casilla de salto
			this.tablero.moverAJugador(user, siguiente); //Mueve al jugador
			moveUser(user);
			sendMsg(user.getNick()+" mueve a casilla "+user.getCasilla().getPos());
			Thread.sleep(2000);
			if (user.getCasilla().getPos()==62) { // Llegada
				win(user);
				return;
			}else { //Mensaje de que ha movido
				sendMsg(user.getCasilla().getMensaje()); //Por ejemplo de oca a oca
				Thread.sleep(1000);
				setTurnoAndSend(this.jugadorConElTurno);
				return;
			}
		}
		if (user.getCasilla().getPos()==57) { // Muerte
			sendMsg(user.getNick()+" ha caido en la casilla de muerte,vuelve a la casilla 0");
			this.tablero.moverAJugador(user, this.tablero.getCasillas()[0]);
			nextTurno();
			return;
		}
		if (user.getCasilla().getPos()==62) {
			win(user);
			sendMsg("Ha ganado "+user.getNick());
			return;
		}
		sancionarSinTirar(user);
		nextTurno();
	}
	private void win(User user) {
		sendMsg("Ha ganado "+user.getNick());
		user.setScore(user.getScore()+10);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		sendWinner(user);
		finishGame();
		
	}
	private void sendWinner(User user) {
		JSONObject data = new JSONObject();
		data.put("winner", user.getNick());
		sendBroadcastSala(data, "WIN");
	}
	private void finishGame() {
		for(int i=1;i<=4;i++) {
			players.remove(i).exitSala();
		}
	}

	public void abortGame(User user) throws Exception{
		JSONObject data = new JSONObject();
		data.put("abort", user.getNick());
		sendBroadcastSala(data, "ABORT");
		finishGame();
	}
	
	private void sancionarSinTirar(User user) {
		int turnosSinTirar=user.getCasilla().getTurnosSinTirar(); //Si ha caido en una casilla de carcel, etc...
		if (turnosSinTirar>0) {
			sendMsg(user.getNick() + " tiene que estar "+turnosSinTirar+" turnos sin tirar");
			user.setTurnosSinTirar(user.getCasilla().getTurnosSinTirar());
		}
	}
	
	private void nextTurno() {
		int next=this.jugadorConElTurno;
		next= next + 1;
		if(next>playersToBeReady) next=1;
		System.out.println("Cambio de turno de :"+this.jugadorConElTurno+" a :"+next);
		setTurnoAndSend(next);
	}

	public void addJugador(User jugador) {
		this.tablero.addJugador(jugador);
	}
	
	public ArrayList<User> getPlayers() {
		ArrayList<User> aux = new ArrayList<>();
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			aux.add(user);
		}
		return aux;
	}
	public int getPlayersSize() {
		return players.size();
	}

	private void moveUser(User user) {
		JSONObject jso = new JSONObject();
		jso.put("player",jugadorConElTurno);
		jso.put("casilla", user.getCasilla().getPos());
		sendBroadcastSala(jso, "MOVE_PLAYER_GAME");
	}
	private void sendMsg(String data) {
		JSONObject aux= new JSONObject();
		aux.put("msg", data);
		sendBroadcastSala(aux, "MSG_GAME");
	}
	@Override
	public String toString() {
		String r="Partida " + this.nameSala+ "\n";
		for(int j=1;j<=this.players.size();j++) {
			User user=this.players.get(j);
			r+="\t" + user.getNick() + "\n";
		}
		r+="\n";
		return r;
	}

	public void sendChat(User user, String msg) {
		JSONObject jso=new JSONObject();
		jso.put("nick", user.getNick());
		jso.put("msg", msg);
		for(int j=1;j<=this.players.size();j++) {
			User aux=this.players.get(j);
			if(!(aux.getNick().equals(user.getNick()))) {
				WebSocketManager.send(aux, jso, "CHAT");
			}
		}
		System.out.println("DEBUG");
	}
}
