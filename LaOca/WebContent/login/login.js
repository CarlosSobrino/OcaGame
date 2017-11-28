"use strict";
function validate(){
	var login = {
		email : document.getElementById("email").value,
		pwd : document.getElementById("pwd").value
	}
	var request = new XMLHttpRequest();
	request.open("post", "login.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState === 4){
			var respuesta = JSON.parse(request.responseText);
			if(respuesta.result ==="OK"){
				alert(respuesta.mensaje);
			}else{
				alert(respuesta.mensaje);
			}
			//conectarWebSocket();
		}
	};
	request.send("login="+JSON.stringify(login));
}