package edu.uclm.esi.tysweb.laoca.websockets;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.dominio.Manager;
import edu.uclm.esi.tysweb.laoca.dominio.User;
import edu.uclm.esi.tysweb.laoca.dominio.User.StateUser;

@ServerEndpoint(value="/WSServer", configurator=HttpSessionConfigurator.class)
public class WSServer {
	private static ConcurrentHashMap<String, User> sesionesPorUser=new ConcurrentHashMap<>();
	
	@OnOpen
	public void open(Session sesion, EndpointConfig config) {
		HttpSession httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		User user=(User) httpSession.getAttribute("user");
		user.setWSSession(sesion);
		user.setState(StateUser.WAITING_SALA);
		sesionesPorUser.put(sesion.getId(), user);
		WebSocketManager.get();
		WebSocketManager.sendSalasPendientes(user);
	}
	
	@OnClose
	public void exit(Session session) {
		User user =sesionesPorUser.remove(session.getId());
		if(user.getSala() != null) Manager.get().abortSala(user);
	}
	
	@OnMessage
	public void recive(Session session, String msg) {
		//DEBUG MSG
		System.out.println("Recieve: "+msg);
		JSONObject jso=new JSONObject(msg);
		try {
			String type = jso.getString("type");
			WebSocketManager.get().ProcessMsg(sesionesPorUser.get(session.getId()),type,jso );
		} catch (Exception e) {
			JSONObject jso_err=new JSONObject();
			jso_err.put("data", e.getMessage());
			WebSocketManager.send( sesionesPorUser.get(session.getId()), jso_err, "ERROR");
		}
	}

	public static void removeSession(User user) {
		if (user.getWSSession()!=null) {
			sesionesPorUser.remove(user.getWSSession().getId());
			user.setState(StateUser.DISCONECTED);
		}
	}
}
