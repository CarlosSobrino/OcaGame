package edu.uclm.esi.tysweb.laoca.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dominio.Manager;
import edu.uclm.esi.tysweb.laoca.dominio.User;
import edu.uclm.esi.tysweb.laoca.dominio.User.StateUser;

public class WebSocketManager {
	private WebSocketManager(){

	}

	private static class WebSocketManagerHolder{
		static WebSocketManager singleton = new WebSocketManager();
	}

	public static WebSocketManager get() {
		return WebSocketManagerHolder.singleton;
	}
	
	public void ProcessMsg(User user,String type, JSONObject data) throws JSONException, Exception {
		switch (type) {
		case "NEW_SALA":
			Manager.get().crearPartida(user, data.getString("salaName"));
			user.setState(StateUser.INSIDE_SALA);
			sendBroadcastSalasPendientes();
			break;
		case "JOIN_SALA":
			Manager.get().addJugadorSala(user, data.getString("salaName"));
			user.setState(StateUser.INSIDE_SALA);
			sendBroadcastSalasPendientes();
			break;


		default:
			break;
		}

	}
	
	public static void send(User user, JSONObject data, String type) {
		Session sesion=user.getWSSession();
		JSONObject jso=new JSONObject();
		try {
			jso.put("type", type);
			jso.put("data", data);
			sesion.getBasicRemote().sendText(jso.toString());
			
			//DEBUG DE MENSAJE
			System.out.println(jso.toString());
		} catch (IOException e) {
			//TODO NO se si hay que cerrar la conexion aqui
		}
	}

	public static void broadcastSalasJugando(String jugador, JSONObject data, String type,String salaName) {
		ArrayList<User> players = Manager.get().getSalasEnJuego().get(salaName).getPlayers();
		Iterator<User> i = players.iterator();
		while (i.hasNext()) {
			User aux =i.next();
			Session sesion = aux.getWSSession();
			try {
				JSONObject jso=new JSONObject();
				jso.put("type", type);
				jso.put("data", data);
				sesion.getBasicRemote().sendText(jso.toString());
			} catch (IOException e) {
				//sesionesPorId.remove(sesion.getId());
			}
		}
	}
	public static void sendSalasPendientes(User user) {
		JSONObject salas = Manager.get().getInfoSalasPendientes();
		if(user.getState() == StateUser.WAITING_SALA) {
			send(user, salas, "INFO_SALAS");
		}
	}
	public static void sendBroadcastSalasPendientes() {
		Enumeration<User> users = Manager.get().getUsuarios().elements();
		while(users.hasMoreElements()) {
			User aux=users.nextElement();
			sendSalasPendientes(aux);
		}
	}
	
}

