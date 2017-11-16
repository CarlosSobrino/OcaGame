<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String login=request.getParameter("login");
JSONObject jso=new JSONObject(login);
JSONObject respuesta=new JSONObject();
try {
	System.out.println(jso.toString());
	String email=jso.getString("email");
	String pwd=jso.getString("pwd");
	String tipoDeBroker=jso.getString("tipoDeBroker");
	User usuario=Manager.get().login(email, pwd, tipoDeBroker);
	session.setAttribute("usuario", usuario);
	respuesta.put("result", "OK");
	respuesta.put("mensaje", email + " conectado");
}
catch (Exception e) {
	respuesta.put("result", "ERROR");
	respuesta.put("mensaje", e.getMessage());
}
out.println(respuesta.toString());
%>