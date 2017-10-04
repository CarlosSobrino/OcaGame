<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@ page import = "org.json.JSONObject" %>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	String nombreJugador = jso.getString("nombre");
	int numeroDeJugadores = jso.getInt("numeroDeJugadores");
	session.setAttribute("nombreDeUsuario", nombreJugador);
	
	int idPartida = Manager.get().crearPartida(nombreJugador, numeroDeJugadores);
	
	JSONObject respuesta = new JSONObject();
	respuesta.put("result", "OK");
	respuesta.put("idPartida", idPartida);
	out.println(respuesta.toString());
%>

