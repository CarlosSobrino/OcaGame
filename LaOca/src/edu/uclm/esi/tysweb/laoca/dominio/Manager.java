package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;

public class Manager {
	private ConcurrentHashMap<String, User> usuarios;
	private ConcurrentHashMap<String, Sala> salasPendientes;
	private ConcurrentHashMap<Integer, Sala> salasEnJuego;

	private Manager(){
		this.usuarios = new ConcurrentHashMap<>();
		this.salasPendientes = new ConcurrentHashMap<>();
		this.salasEnJuego = new ConcurrentHashMap<>();
	}

	private static class ManagerHolder{
		static Manager singleton = new Manager();
	}

	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	public int crearPartida(String nombreJugador,String salaName) throws Exception {
		if(salasPendientes.containsKey(salaName)) {
			throw new Exception("Error!,Nombre de sala ya usado");
		}
		User user = findUser(nombreJugador);
		Sala partida = new Sala(user, salaName);
		this.salasPendientes.put(salaName, partida);
		return partida.getId();
	}

	private User findUser(String nombreJugador) {
		User usuario = this.usuarios.get(nombreJugador);
		if(usuario == null) {
			usuario = new User(nombreJugador);
			this.usuarios.put(nombreJugador,  usuario);
		}
		return usuario;
	}

	public void addJugadorSala(String nombreJugador,String salaName) throws Exception{
		if(this.salasPendientes.isEmpty())
			throw new Exception("No hay salas pendientes, crea una sala");
		if(!salasPendientes.containsKey(salaName)) {
			throw new Exception("La sala seleccionada no existe");
		}
		if(!salasEnJuego.containsKey(salaName)) {
			throw new Exception("La sala seleccionada no existe");
		}
		Sala partida = this.salasPendientes.elements().nextElement();
		User usuario = findUser(nombreJugador);
		partida.add(usuario);
		if(partida.isReady()) {
			this.salasPendientes.remove(partida.getId());
			this.salasEnJuego.put(partida.getId(), partida);
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

	public ConcurrentHashMap<Integer, Sala> getSalasEnJuego() {
		return salasEnJuego;
	}

	public JSONObject tirarDado(int idPartida, String jugador, int dado) throws Exception {
		Sala partida=this.salasEnJuego.get(idPartida);
		JSONObject mensaje=partida.tirarDado(jugador, dado);
		mensaje.put("idPartida", idPartida);
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
		salasEnJuego.remove(partida.getId());
	}
	
	public User getUserConnected(String nick) {
		return usuarios.get(nick);
	}
}
