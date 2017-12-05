"use strict";
$(document).ready(function(){
	ConnectionManager.isConnected();
});

var ConnectionManager={
	ws:null,
	isConnected: function() {
		var request = new XMLHttpRequest();	
		request.open("get", "../ConnectionManager.jsp");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.onreadystatechange=function() {
			if (request.readyState===4) {
				var respuesta=JSON.parse(request.responseText);
				if (respuesta.result==="OK") {
					//TODO hacer que una variable del html adquiera el valor al logearse
					alert("Si estas logeado");
				}
			}
		};
		request.send();	
	},
	connectWebSocket: function(){
		this.ws=new WebSocket("ws://localhost:8080/LaOca/servidorDePartidas");
		
		this.ws.onopen = function() {
			addMensaje("Websocket conectado");
			alert("WebSocket connected");
		}	
		this.ws.onmessage = function(datos) {
			var mensaje=datos.data;
			mensaje=JSON.parse(mensaje);
			if (mensaje.tipo=="DIFUSION") {
				addMensaje(mensaje.mensaje);
			} else if (mensaje.tipo=="COMIENZO") {
				addMensaje("Comienza la partida");
				comenzar(mensaje);
			}
		}	 
	}
}
