package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.concurrent.ConcurrentHashMap;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;

public class Manager {
	private ConcurrentHashMap<String, User> usuarios;
	private ConcurrentHashMap<Integer, Partida> partidasPendientes;
	private ConcurrentHashMap<Integer, Partida> partidasEnJuego;
	
	private Manager(){
		this.usuarios = new ConcurrentHashMap<>();
		this.partidasPendientes = new ConcurrentHashMap<>();
		this.partidasEnJuego = new ConcurrentHashMap<>();
	}
	
	private static class ManagerHolder{
		static Manager singleton = new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}
	
	public int crearPartida(String nombreJugador, int numeroDeJugadores) {
		User usuario = findUser(nombreJugador);
		Partida partida = new Partida(usuario, numeroDeJugadores);
		this.partidasPendientes.put(partida.getId(), partida);
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
	
	public void addJugador(String nombreJugador) throws Exception{
		if(this.partidasPendientes.isEmpty())
			throw new Exception("No hay hijueputas partidas pendientes. Crea una, pendejo");
		Partida partida = this.partidasPendientes.elements().nextElement();
		User usuario = findUser(nombreJugador);
		partida.add(usuario);
		if(partida.isReady()) {
			this.partidasPendientes.remove(partida.getId());
			this.partidasEnJuego.put(partida.getId(), partida);
		}
	}
	//TODO Maybe it should return the user
	public boolean login(String email,String pwd) throws Exception{
		User user=new UserRegistered(email,pwd);
		return user.loginDB();
		//TODO insert user in the list
	}
	
	//TODO Maybe it should return the user
	public boolean registrar(String email,String pwd) throws Exception{
		User user = new UserRegistered();
		//TODO insert user in the list
		return user.insertIntoDB();
	}
	
	//TODO Maybe it should return the user
	public boolean changePassword(String email,String pwd,String newPassword) throws Exception{
		User user= this.usuarios.get(email);
		if(user != null) {
			user.changePasswordDB(newPassword);
			return true;
		}
		return false;
	}

	
	public void logoff(String email) {
		this.usuarios.remove(email);
	}

}
