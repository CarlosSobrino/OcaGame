<%@page import="edu.uclm.esi.tysweb.laoca.dominio.Manager"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "org.json.JSONObject" %>

<%
	System.out.println("Entra aqui");
	String p = request.getParameter("p");
	JSONObject jso = new JSONObject(p);
	JSONObject respuesta = new JSONObject(p);
	try{
		String email = jso.optString("email");
		String pwd1 = jso.optString("pass1");
		String pwd2 = jso.optString("pass2");
		
		comprobarCredenciales(email,pwd1,pwd2);
		Manager.get().registrar(email, pwd1);
		respuesta.put("result","OK");
		System.out.println(respuesta.toString());
		
	}catch(Exception e){
		//No devolver el error directamente sino captar el code y devolver un mensaje predefinido
		respuesta.put("result","ERROR");
		//respuesta.put("mensaje",e.getMessage());
		System.out.println(respuesta.toString());
	}
	
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