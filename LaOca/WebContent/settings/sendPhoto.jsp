<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Enviar foto</title>
</head>
<body>
	<%

	%>

	<form action="SolicitarPwd.jsp" method="POST" enctype="multipart/form-data">
		Sube tu foto de perfil
		<input type="file" placeholder="Selecciona tu foto" name="foto" accept="image/x-png,image/gif,image/jpeg"> <br>
		<button type = "submit">Enviar</button>
	</form>
	
	<%
	
	%>
</body>
</html>

<%!


%>