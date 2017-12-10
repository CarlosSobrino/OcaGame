<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	System.out.println("entrando");
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	JSONObject respuesta = new JSONObject();
	try{
		String email = jso.getString("email");
		String pwd1 = jso.getString("pwd1");
		String pwd2 = jso.getString("pwd2");
		String nick = jso.getString("nick");
		System.out.println(email);
		comprobarCredenciales(email,pwd1,pwd2);
		User user = Manager.get().registrar(email, pwd1,nick);
		if(user != null){
			session.setAttribute("user", user);
			respuesta.put("result","OK");
			respuesta.put("mensaje","Usuario registrado correctamente");
		}else{
			throw new Exception("No se pudo registrar el usuario");
		}
		System.out.println(respuesta.toString());
		
	}catch(Exception e){
		//No devolver el error directamente sino captar el code y devolver un mensaje predefinido
		respuesta.put("result","ERROR");
		respuesta.put("mensaje",e.getMessage());
		 
	}
	out.println(respuesta.toString());
%>

<%!
	private void comprobarCredenciales(String email, String pwd1, String pwd2) throws Exception {
    if(email.length()==0){
      throw new Exception("El email no puede estar vacío");
    }
    if(!pwd1.equals(pwd2)){
      throw new Exception("Las contraseñas no coindicen");
    }
    if(pwd1.length()<4){
      throw new Exception("La contraseña tiene que tener 4 caracteres por lo menos");
    }
  } 
%>