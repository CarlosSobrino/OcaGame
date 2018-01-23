"use strict";
$(document).ready(function(){
		if(getCookie("pwd")!=undefined){
			document.getElementById("pwd").value = getCookie("pwd");
		}
});
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
				 location.href ="../index.html";
			}else{
				//TODO Reflejar el mensaje en rojo debajo del email
				alert(respuesta.mensaje);
			}
		}
	};
	request.send("login="+JSON.stringify(login));
}

function getCookie(name) {
	  var value = "; " + document.cookie;
	  var parts = value.split("; " + name + "=");
	  if (parts.length == 2) return parts.pop().split(";").shift();
	}