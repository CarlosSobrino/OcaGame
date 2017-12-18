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

public class WebSocketManager {
	private WebSocketManager(){

	}

	private static class WebSocketManagerHolder{
		static WebSocketManager singleton = new WebSocketManager();
	}

	public static WebSocketManager get() {
		return WebSocketManagerHolder.singleton;
	}
	
	public void ProcessMsg(String type, JSONObject data,String nick) throws JSONException, Exception {
		switch (type) {
		case "NEW_SALA":
			Manager.get().crearPartida(nick, data.getString("salaName"));
			break;
		case "JOIN_SALA":
			Manager.get().addJugadorSala(nick, data.getString("salaName"));
			break;


		default:
			break;
		}

	}
	
	public static void send(String jugador, JSONObject data, String type) {
		User user = Manager.get().getUserConnected(jugador);
		Session sesion=user.getWSSession();
		JSONObject jso=new JSONObject();
		try {
			jso.put("type", type);
			jso.put("data", data);
			sesion.getBasicRemote().sendText(jso.toString());
		} catch (IOException e) {
			//TODO NO se si hay que cerrar la conexion aqui
		}
	}

	public static void broadcast(String jugador, JSONObject data, String type,String salanameSala) {
		ArrayList<User> players = Manager.get().getSalasEnJuego().get(salanameSala).getPlayers();
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
}

