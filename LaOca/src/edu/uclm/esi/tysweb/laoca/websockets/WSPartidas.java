package edu.uclm.esi.tysweb.laoca.websockets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint(value = "/servidorDePartidas", configurator=HttpSessionConfigurator.class)
public class WSPartidas {
	private static ConcurrentHashMap<String, Session> sesionesPorId = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Session> sesionesPorNombre = new ConcurrentHashMap<>();
	
	@OnOpen
	public void open(Session sesion, EndpointConfig config) {
		HttpSession httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		String nombreDeUsuario = httpSession.getAttribute("nombreDeUsuario").toString();
		System.out.println("Sesión " + sesion.getId());
		sesionesPorId.put(sesion.getId(), sesion);
		sesionesPorNombre.put(nombreDeUsuario, sesion);
		broadcast("Ha llegado: "+ nombreDeUsuario);
	}
	
	@OnClose
	public void usuarioSeVa(Session sesion) {
		sesionesPorId.remove(sesion.getId());
		broadcast("Se ha ido un usuario");
	}

	private void broadcast(String mensaje) {
		Enumeration<Session> sesiones = sesionesPorId.elements();
		while(sesiones.hasMoreElements()) {
			Session sesion = sesiones.nextElement();
			try {
				JSONObject jso = new JSONObject();
				jso.put("tipo", "DIFUSION");
				jso.put("mensaje", mensaje);
				sesion.getBasicRemote().sendText(jso.toString());
			} catch (IOException e) {
				sesionesPorId.remove(sesion.getId());
			}
		}
	}
}
