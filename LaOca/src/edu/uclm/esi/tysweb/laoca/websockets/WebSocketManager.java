package edu.uclm.esi.tysweb.laoca.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.websocket.RemoteEndpoint;
import javax.websocket.RemoteEndpoint.Async;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
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
		try {
			switch (type) {
			case "NEW_SALA":
				Manager.get().crearSala(user, data.getString("data"));
				user.setState(StateUser.INSIDE_SALA);
				sendBroadcastSalasPendientes();
				send(user,null,"LOAD_GAME");
				break;
			case "JOIN_SALA":
				Manager.get().addJugadorSala(user, data.getString("data"));
				user.setState(StateUser.INSIDE_SALA);
				sendBroadcastSalasPendientes();
				send(user,null,"LOAD_GAME");
				break;
			case "READY":
				user.setState(StateUser.READY);
				user.getSala().isReady();
				break;
			case "DADO_GAME":
				int dado=data.getInt("data");
				user.getSala().tirarDado(user, dado);
				break;
			case "CHAT":
				user.getSala().sendChat(user, data.getString("data"));
				break;
			default:
				break;
			}
		}catch (Exception e) {
			System.out.println("Error! "+e.getMessage()+" - "+e.toString()+" - "+e.getLocalizedMessage()
			);
		}


	}
	/*public static void setTimeoutUser(User user,String msg) {
		Async asyncHandle = user.getWSSession().getAsyncRemote();
		asyncHandle.setSendTimeout(10000); //2000 ms
		Future<Void> tracker = asyncHandle.sendText(msg); //will timeout after 2 seconds
		try {
			tracker.get();
			if(tracker.isDone()) {
				disconnect(user);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	*/
	public void setTimeoutUser(User user, String msg){
		System.out.println("StartTimeOUT "+user.getNick());
		Async asyncHandle = user.getWSSession().getAsyncRemote();
		asyncHandle.setSendTimeout(10000); //1 second
		asyncHandle.sendText(msg, 
				new SendHandler(){
		      	@Override
		      		public void onResult(SendResult result) {
				        if(!result.isOK()){
				        	System.out.println("Async failure: "+ result.getException());
				        }else {
				        	System.out.println("TimeOut finish "+user.getNick());
				        }
		      		}
		  		}); //will timeout after 2 seconds
		
	}
	
	public static void disconnect(User user) {
		System.out.println("Timeout");
	}
	public static void send(User user, JSONObject data, String type) {
		Session sesion=user.getWSSession();
		JSONObject jso=new JSONObject();
		try {
			jso.put("type", type);
			jso.put("data", data);
			sesion.getBasicRemote().sendText(jso.toString());
			
			//DEBUG DE MENSAJE
			System.out.println("Send WS: "+jso.toString());
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

