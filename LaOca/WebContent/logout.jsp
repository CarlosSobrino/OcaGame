<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.tysweb.laoca.dominio.*, org.json.*" %>
    
<%
	JSONObject jso=new JSONObject();
	User usuario=(User) session.getAttribute("user");
	if (usuario==null)
		jso.put("result", "ERROR");
	else{
		try{
			if(Manager.get().logout(usuario.getNick()).getNick() == usuario.getNick()){
				jso.put("result", "OK");
				session.setAttribute("user", null);
			}else{
				jso.put("result", "ERROR");
			}
		}catch(Exception e){
			jso.put("result", "ERROR");
		}
	}
	out.print(jso.toString());
%>