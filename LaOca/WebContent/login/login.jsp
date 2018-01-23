<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String login=request.getParameter("login");
JSONObject jso=new JSONObject(login);
JSONObject respuesta=new JSONObject();
try {
	String email=jso.getString("email");
	String pwd=jso.getString("pwd");
	User user=Manager.get().login(email, pwd);
	if(user != null){
		session.setAttribute("user", user);
		respuesta.put("result", "OK");
		respuesta.put("mensaje", email + " conectado");
		Cookie cookie=new Cookie("pwd", pwd);
		cookie.setMaxAge(3000000);
		response.addCookie(cookie);
	}else{
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", "Email o contraseña incorrectos");
	}

}
catch (Exception e) {
	respuesta.put("result", "ERROR");
	respuesta.put("mensaje", "Email o contraseña incorrectos");
}
out.println(respuesta.toString());
%>