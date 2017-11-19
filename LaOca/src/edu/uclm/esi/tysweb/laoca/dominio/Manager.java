package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.concurrent.ConcurrentHashMap;

import edu.uclm.esi.tysweb.laoca.presistencia.DAOUsuario;

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
	
	public void registrar(String email,String pwd) throws Exception{
		User user = new UserRegistered();
		user.SetEmail(email);
		DAOUsuario.insert(email, pwd);
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
	
	public boolean loginSinPool(String email,String pwd) throws Exception{
		User user=new UserRegistered(email,pwd);
		if(DAOUsuario.existeUser(user)){
			this.usuarios.put(email,user);
			return true;
		}
		return false;
	}
	
	public User login(String email, String pass, String tipoDeBroker) throws Exception {
		User usuario=new User(email);
		if (tipoDeBroker.equals("conPool")) {
			if (!usuario.existeConPool(pass))
				throw new Exception("Usuario o contrase�a invalidos");
		} else if (tipoDeBroker.equals("abriendoYCerrandoConexion")) {
			if (!usuario.existeAbriendoYCerrando(pass))
				throw new Exception("Usuario o contraseña inválidos");
		} else 
			throw new Exception("Broker desconocido");
		this.usuarios.put(email, usuario);
		return usuario;
	}
	
	public void logoff(String email) {
		this.usuarios.remove(email);
	}

}
