"use strict";
function validate(){
	var pwd = {
		old_pwd : document.getElementById("old_pwd").value,
		new_pwd : document.getElementById("new_pwd").value
	}
	var request = new XMLHttpRequest();
	request.open("post", "cambiarpwd.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState === 4){
			var respuesta = JSON.parse(request.responseText);
			if(respuesta.result ==="OK"){
				 alert("Contraseña cambiada con éxito");
			}else{
				alert(respuesta.mensaje);
			}
		}
	};
	request.send("pwd="+JSON.stringify(pwd));
}