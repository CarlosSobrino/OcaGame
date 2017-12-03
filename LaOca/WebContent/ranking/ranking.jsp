<%@page import="org.json.JSONObject, edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>

<%
JSONObject jso=new JSONObject();

try {
	jso.put("result", "OK");
	jso.put("ranking",Manager.get().getScores());
}
catch (Exception e) {
	jso.put("result", "ERROR");
}
out.println(jso.toString());
%>