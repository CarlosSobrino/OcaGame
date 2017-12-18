package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.websockets.WSServer;

public class Sala {

	private String nameSala;
	private ArrayList<User> players = new ArrayList<User>();
	public ArrayList<User> getPlayers() {
		return players;
	}

	private int playersToBeReady;
	private int id;
	private int jugadorConElTurno;
	private Tablero tablero;
	private User ganador;

	public Sala(User creador,String nameSala) {
		players.add(creador);
		this.playersToBeReady=4;
		this.id=new Random().nextInt();
		this.tablero=new Tablero();
		this.nameSala = nameSala;
	}

	public int getId() {
		return this.id;
	}

	public void add(User jugador) throws Exception {
		if(players.size() >= playersToBeReady) {
			throw new Exception("Sala ya llena");
		}
		players.add(jugador);

	}
	public String getName() {
		return nameSala;
	}

	public boolean isReady() {
		return this.players.size()==this.playersToBeReady;
	}

	public void comenzar() {
		JSONObject jso=new JSONObject();
		jso.put("tipo", "COMIENZO");
		jso.put("idPartida", this.id);
		JSONArray jsa=new JSONArray();
		this.jugadorConElTurno=(new Random()).nextInt(this.players.size());
		jso.put("jugadorConElTurno", getJugadorConElTurno().getEmail());
		for (User jugador : players) 
			jsa.put(jugador.getEmail());
		jso.put("players", jsa);
		//broadcast(jso);
	}

	public User getJugadorConElTurno() {
		if (this.players.size()==0)
			return null;
		return this.players.get(this.jugadorConElTurno);
	}

	public JSONObject tirarDado(String nombreJugador, int dado) throws Exception {
		JSONObject result=new JSONObject();
		User jugador=findJugador(nombreJugador);
		if (jugador!=getJugadorConElTurno())
			throw new Exception("No tienes el turno");
		result.put("tipo", "TIRADA");
		result.put("casillaOrigen", jugador.getCasilla().getPos());
		result.put("dado", dado);
		Casilla destino=this.tablero.tirarDado(jugador, dado);
		result.put("destinoInicial", destino.getPos());
		Casilla siguiente=destino.getSiguiente();
		boolean conservarTurno=false;
		if (siguiente!=null) {
			conservarTurno=true;
			String mensaje=destino.getMensaje();
			result.put("destinoFinal", siguiente.getPos());
			result.put("mensaje", mensaje);
			this.tablero.moverAJugador(jugador, siguiente);
			if (siguiente.getPos()==62) { // Llegada
				this.ganador=jugador;
				result.put("ganador", this.ganador.getEmail());
			}
		}
		if (destino.getPos()==57) { // Muerte
			jugador.setPartida(null);
			result.put("mensaje", jugador.getEmail() + " cae en la muerte");
			this.players.remove(jugador);
			this.jugadorConElTurno--;
			if (this.players.size()==1) {
				this.ganador=this.players.get(0);
				result.put("ganador", this.ganador.getEmail());
			}
		}
		if (destino.getPos()==62) { // Llegada
			this.ganador=jugador;
			result.put("ganador", this.ganador.getEmail());
		}
		int turnosSinTirar=destino.getTurnosSinTirar();
		if (turnosSinTirar>0) {
			result.put("mensajeAdicional", jugador.getEmail() + " está " + turnosSinTirar + " turnos sin tirar porque ha caído en ");
			jugador.setTurnosSinTirar(destino.getTurnosSinTirar());
		}
		result.put("jugadorConElTurno", pasarTurno(conservarTurno));
		return result;
	}

	private String pasarTurno(boolean conservarTurno) {
		if (!conservarTurno) {
			boolean pasado=false;
			do {
				this.jugadorConElTurno=(this.jugadorConElTurno+1) % this.players.size();
				User jugador=getJugadorConElTurno();
				int turnosSinTirar=jugador.getTurnosSinTirar();
				if (turnosSinTirar>0) {
					jugador.setTurnosSinTirar(turnosSinTirar-1);
				} else
					pasado=true;
			} while (!pasado);
		}
		return getJugadorConElTurno().getEmail();
	}

	private User findJugador(String nombreJugador) {
		for (User jugador : players)
			if (jugador.getEmail().equals(nombreJugador))
				return jugador;
		return null;
	}

	public void addJugador(User jugador) {
		this.tablero.addJugador(jugador);
	}
	

	public User getGanador() {
		return this.ganador;
	}

	public void terminar() {
		for (User jugador : this.players)
			jugador.setPartida(null);
	}
	
	@Override
	public String toString() {
		String r="Partida " + id + "\n";
		for (User jugador : players)
			r+="\t" + jugador + "\n";
		r+="\n";
		return r;
	}
}
