<%@page import="edu.uclm.esi.tysweb.laoca.dominio.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Recuperación de password</title>
</head>
<body>
	<%
		String email = request.getParameter("email_forget");
	%>

	<form action="solicitarPwd.jsp" method="GET">
		<input type="text" placeholder="Correo electronico" name="email_forget"> <br>
		<button type = "submit">Enviar</button>
	</form>
	
	<%
	if(email==null){	
		out.print("Introduzca un email");
		} else{
			try{
				enviarToken(email);
				out.print("Visita tu correo para cambiar tu email");
			}catch(Exception e){
				out.print(e.getMessage());				
			}
		}
	
	%>
</body>
</html>

<%!
	private void enviarToken(String email) throws Exception{
		long token = new java.util.Random().nextLong();
		if(token<0){
			token= (-token);
			//TODO crear class token con System.currentTimeMillis()+ 24*60*60*1000 como caducidad, token e email
		}
		EMailSenderService ess = new EMailSenderService();
		ess.enviarPorGmail(email, token);
	}
%>