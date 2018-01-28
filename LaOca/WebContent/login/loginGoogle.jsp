<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%

String login=request.getParameter("login");
JSONObject jso=new JSONObject(login);
JSONObject respuesta=new JSONObject();
try {
	String googleId=jso.getString("googleId");
	String email = jso.getString("email");
	String nick = jso.getString("nick");
	//Comprobar id de google
	User user = new User(email);
	if(user != null){
		user.setNick(nick);
		user.googleUser();
		Manager.get().getUsuarios().put(nick, user);
		session.setAttribute("user", user);
		respuesta.put("result", "OK");
		respuesta.put("mensaje", email + " conectado");
		respuesta.put("google", user.getGoogle());
	}else{
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", "Fallo al logearse");
	}

}
catch (Exception e) {
	respuesta.put("result", "ERROR");
	respuesta.put("mensaje", "Email o contraseña incorrectos");
}
out.println(respuesta.toString());
%>