<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String user = request.getParameter("user");
JSONObject jso = new JSONObject(user);
JSONObject respuesta = new JSONObject();
try {
	String nick=jso.getString("nick");
	//TODO
	//Credentials.validateNick(nick)
	//Comprobar si existe en la base de datos -> Manager.get().ExistNick() false no existe true existe. Llamar a DAOUSER.
	if(nick != ""){
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