<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String user = request.getParameter("user");
JSONObject jso = new JSONObject(user);
JSONObject respuesta = new JSONObject();
try {
	String nick=jso.getString("nick");
	//String tipoDeBroker=jso.getString("tipoDeBroker");
	if(!nick.contains("\n") || !nick.contains("\t") || !nick.contains(" ")){
		respuesta.put("result", "OK");
		respuesta.put("mensaje", nick + " conectado");
	}else{
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", "Nick invalido");
	}
	//User usuario=Manager.get().login(email, pwd, tipoDeBroker);
	//session.setAttribute("usuario", usuario);

}
catch (Exception e) {
	respuesta.put("result", "ERROR");
	respuesta.put("mensaje", "Nick invalido");
}
out.println(respuesta.toString());
%>