package edu.uclm.esi.tysweb.laoca.websockets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

import edu.uclm.esi.tysweb.laoca.dominio.Manager;
import edu.uclm.esi.tysweb.laoca.dominio.Sala;
import edu.uclm.esi.tysweb.laoca.dominio.User;

@ServerEndpoint(value="/WSServer", configurator=HttpSessionConfigurator.class)
public class WSServer {
	private static ConcurrentHashMap<String, User> sesionesPorUser=new ConcurrentHashMap<>();
	
	@OnOpen
	public void open(Session sesion, EndpointConfig config) {
		HttpSession httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		User User=(User) httpSession.getAttribute("user");
		User.setWSSession(sesion);
		System.out.println("Sesion " + sesion.getId());
		sesionesPorUser.put(sesion.getId(), User);
		/*
		broadcast("Ha llegado " + User.getEmail());
		Partida partida=User.getPartida();
		if (partida.isReady())
			partida.comenzar();
		*/
	}
	
	@OnClose
	public void exit(Session session) {
		sesionesPorUser.remove(session.getId());
		/*
		Enumeration<String> eNombres = sesionesPorUser.keys();
		String nombre;
		while (eNombres.hasMoreElements()) {
			nombre=eNombres.nextElement();
			Session sesion=sesionesPorUser.get(nombre);
			if (sesion.getId().equals(session.getId())) {
				sesionesPorNombre.remove(nombre);
				broadcast("Se ha ido " + nombre);
				break;
			}
		}*/
	}
	
	@OnMessage
	public void recive(Session session, String msg) {
		JSONObject jso=new JSONObject(msg);
		String type = jso.getString("type");
		try {
			WebSocketManager.get().ProcessMsg(type,jso.getJSONObject("data"), sesionesPorUser.get(session.getId()).getNick());
		} catch (Exception e) {
			JSONObject jso_err=new JSONObject();
			jso_err.put("data", e.getMessage());
			WebSocketManager.send( sesionesPorUser.get(session.getId()).getNick(), jso_err, "ERROR");
		}
	}

	public static void removeSession(User jugador) {
		if (jugador.getWSSession()!=null) {
			sesionesPorUser.remove(jugador.getWSSession().getId());
		}
	}
}
