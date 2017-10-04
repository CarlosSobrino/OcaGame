function crearPartida(){
	var request = new XMLHttpRequest();
	request.open("post", "crearPartida.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			console.log(respuesta.result);
			conectarWebSocket();
		}
	};
	var p = {
			nombre : document.getElementById("nombre").value,
			numeroDeJugadores : document.getElementById("numero").value
	};
	request.send("p=" + JSON.stringify(p));
}

function unirse(){
	var request = new XMLHttpRequest();
	request.open("post", "llegarASalaDeEspera.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			console.log(respuesta.result);
			conectarWebSocket();
		}
	};
	var p = {
			nombre : document.getElementById("nombre").value
	};
	request.send("p=" + JSON.stringify(p));
}

var ws;

function conectarWebSocket(){
	ws = new WebSocket("ws://localhost:8080/LaOca/servidorDePartidas");
	ws.onopen = function() {
		console.log("Websocket conectado");
	}
	ws.onmessage = function(datos){
		var mensaje = datos.data;
		mensaje = JSON.parse(mensaje);
		if(mensaje.tipo=="DIFUSION"){
			console.log(mensaje, mensaje);
		}
	}
}