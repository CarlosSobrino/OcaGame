'use strict';
function validate(){
	var login = {
		email : document.getElementById("email").value,
		pwd : document.getElementById("pwd").value
	}
	var request = new XMLHttpRequest();
	request.open("post", "login/login.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState === 4){
			var respuesta = JSON.parse(request.responseText);
			alert(respuesta);
			//conectarWebSocket();
		}
	};
	request.send("login="+JSON.stringify(login));
}