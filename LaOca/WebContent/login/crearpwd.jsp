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
		String code = request.getParameter("code");
		String pwd = request.getParameter("pwd");
	%>
	<form action="crearpwd.jsp" method="GET">
		<input type="text" placeholder="Contraseña" name="pwd"> <br>
		<button type="submit">Enviar</button>
	</form>
	<%
		System.out.print(code+" "+pwd);
		if (pwd == null && code != null) {
			Cookie cookie=new Cookie("code", code);
			cookie.setMaxAge(300000);
			response.addCookie(cookie);
			out.print("Introduzca una contraseña");
		}else if(pwd != null && code == null){
			Cookie cookie =null;
		 	Cookie[] cookies = null;
		 	cookies = request.getCookies();
			      for (int i = 0; i < cookies.length; i++){
			    	  
			          cookie = cookies[i];
			  		System.out.println(cookie.getName()+" "+cookie.getValue());
			          if(cookie.getName().equals("code")){
			        	  Manager.get().changeRecoverCodePwd(cookie.getValue(), pwd);
			          }
			      }
			}
	%>
</body>
</html>