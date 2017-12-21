package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dominio.User.StateUser;
import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;

public class Manager {
	private ConcurrentHashMap<String, User> usuarios;
	private ConcurrentHashMap<String, Sala> salasPendientes;
	private ConcurrentHashMap<String, Sala> salasEnJuego;

	private Manager(){
		this.usuarios = new ConcurrentHashMap<>();
		this.salasPendientes = new ConcurrentHashMap<>();
		this.salasEnJuego = new ConcurrentHashMap<>();
	}

	public ConcurrentHashMap<String, User> getUsuarios() {
		return usuarios;
	}

	private static class ManagerHolder{
		static Manager singleton = new Manager();
	}

	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	public String crearPartida(User user,String salaName) throws Exception {
		if(!(user.getState() == StateUser.WAITING_SALA)) {
			throw new Exception("Ya estas en una partida");
		}
		if(salasPendientes.containsKey(salaName)) {
			throw new Exception("Error!,Nombre de sala ya usado");
		}
		Sala partida = new Sala(user, salaName);
		this.salasPendientes.put(salaName, partida);
		return partida.getName();
	}

	public User findUser(String nombreJugador) {
		User usuario = this.usuarios.get(nombreJugador);
		if(usuario == null) {
			usuario = new User(nombreJugador);
			this.usuarios.put(nombreJugador,  usuario);
		}
		return usuario;
	}

	public void addJugadorSala(User user,String salaName) throws Exception{
		if(!(user.getState() == StateUser.WAITING_SALA)) {
			throw new Exception("Ya estas en una partida");
		}
		if(this.salasPendientes.isEmpty())
			throw new Exception("No hay salas pendientes, crea una sala");
		if(!salasPendientes.containsKey(salaName)) {
			throw new Exception("La sala seleccionada no existe");
		}
		if(salasEnJuego.containsKey(salaName)) {
			throw new Exception("La sala seleccionada ya en juego");
		}
		Sala sala = this.salasPendientes.elements().nextElement();
		sala.add(user);
		if(sala.isReady()) {
			//this.salasPendientes.remove(sala.getName());
			this.salasEnJuego.put(sala.getName(), sala);
		}
	}

	public User login(String email,String pwd) throws Exception{
		User user = DAOUser.login(email,pwd);
		if(user != null) {
			String nick = user.getNick();
			if(nick != null) {
				usuarios.put(nick, user);
			}
		}
		return user;
	}

	public User registrar(String email,String pwd,String nick) throws Exception{
		User user = DAOUser.insert(email, pwd, nick);
		usuarios.put(nick, user);
		return  user;
	}

	public boolean jugarSinRegistrar(String nick) throws Exception{
		User user = null;
		if (!usuarios.containsKey(nick)) {
			user = new UserUnregistered(nick);
			usuarios.put(nick, user);
		}
		return user != null;
	}

	public boolean changePassword(String email,String pwd,String newPassword) throws Exception{
		User user = DAOUser.login(email, pwd);
		String nick = user.getNick();
		boolean aux=false;
		if(user != null) {
			user.changePasswordDB(newPassword);
			usuarios.put(nick, user);
			aux=true;
		}
		return aux;
	}


	public User logout(String nick) {
		return this.usuarios.remove(nick);
	}
	
	public ConcurrentHashMap<String, Sala> getSalasPendientes() {
		return salasPendientes;
	}

	public ConcurrentHashMap<String, Sala> getSalasEnJuego() {
		return salasEnJuego;
	}

	public JSONObject tirarDado(String salaName, String jugador, int dado) throws Exception {
		Sala partida=this.salasEnJuego.get(salaName);
		JSONObject mensaje=partida.tirarDado(jugador, dado);
		mensaje.put("idPartida", salaName);
		mensaje.put("jugador", jugador);
		//partida.broadcast(mensaje);
		if (mensaje!=null && mensaje.opt("ganador")!=null) {
			terminar(partida);
		}
		return mensaje;
	}

	public JSONObject getScores() throws Exception {
		return DAOUser.getScoreList();
	}

	private void terminar(Sala partida) {
		partida.terminar();
		salasEnJuego.remove(partida.getName());
	}
	
	public JSONObject getInfoSalasPendientes() {
		JSONObject salas_data = new JSONObject();
		try {

			Enumeration<Sala> i = salasPendientes.elements();
			JSONArray salas = new JSONArray();
			while(i.hasMoreElements()) {
				Sala aux = i.nextElement();
				JSONObject sala = new JSONObject();
				sala.put("name", aux.getName());
				sala.put("players", aux.getPlayersSize());
				salas.put(sala);
			}
			salas_data.put("salas", salas);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return salas_data;
	}
}
