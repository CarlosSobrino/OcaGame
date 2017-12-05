<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="edu.uclm.esi.tysweb.laoca.dominio.*, org.json.*" %>
    
<%
	JSONObject jso=new JSONObject();
	User usuario=(User) session.getAttribute("user");
	if (usuario==null)
		jso.put("result", "ERROR");
	else
		jso.put("result", "OK");
	out.print(jso.toString());
%>