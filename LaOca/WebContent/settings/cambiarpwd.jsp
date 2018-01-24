<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
String login=request.getParameter("pwd");
JSONObject jso=new JSONObject(login);
JSONObject respuesta=new JSONObject();
Boolean flag = false;
try {
	String old_pwd=jso.getString("old_pwd");
	String new_pwd=jso.getString("new_pwd");
	User user = (User) session.getAttribute("user");
	if(user != null){
		System.out.println(user.getNick());
		flag = user.changePasswordDB(old_pwd, new_pwd);
	}
	if(flag == true){
		respuesta.put("result", "OK");
		respuesta.put("mensaje", "Cambio de contraseña correcto");
		Cookie cookie=new Cookie("pwd", new_pwd);
		cookie.setMaxAge(3000000);
		response.addCookie(cookie);
	}else{
		respuesta.put("result", "ERROR");
		respuesta.put("mensaje", "Contraseña antigua incorrecta");
	}

}
catch (Exception e) {
	respuesta.put("result", "ERROR");
	respuesta.put("mensaje", "Contraseña antigua incorrecta");
}
out.println(respuesta.toString());
%>