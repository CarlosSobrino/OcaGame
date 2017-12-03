"use strict";
function registrar(){
	var p = {
			email : document.getElementById("email").value,
			pwd1 : document.getElementById("pwd1").value,
			pwd2 : document.getElementById("pwd2").value,
			nick: document.getElementById("nick").value
	};
	var request = new XMLHttpRequest();
	request.open("post", "registrar.jsp");
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
	request.send("p=" + JSON.stringify(p));
}