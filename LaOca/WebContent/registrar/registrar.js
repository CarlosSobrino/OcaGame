function registrar(){
	var request = new XMLHttpRequest();
	request.open("post", "registrar.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			alert(respuesta);
			if(respuesta.result=="OK"){
				divRegistro.style="display:none";
			}else{
				mensajeRegistro.innerHTML=respuesta.mensaje;
			}
		}
	};
	var p = {
			user : user.value,
			pass1 : pass1.value,
			pass2 :pass2.value
	};
	request.send("p=" + JSON.stringify(p));
}