<%@page
	import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String user = request.getParameter("user");
	JSONObject jso = new JSONObject(user);
	JSONObject respuesta = new JSONObject();
	try {
		String nick = jso.getString("nick");
		if (nick.contains(" ") || nick.contains("\n") || nick.contains("\t")) {
			respuesta.put("mensaje", "Nick invalido");
		} else {
			User usuario = Manager.get().jugarSinRegistrar(nick);
			if (usuario != null) {
				session.setAttribute("user", usuario);
				respuesta.put("result", "OK");
				respuesta.put("mensaje", nick + " conectado");
			} else {
				respuesta.put("result", "ERROR");
				respuesta.put("mensaje", "Nick invalido");
			}
		}
		
	} catch (Exception e) {
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", "Nick invalido");
	}
	out.println(respuesta.toString());
%>