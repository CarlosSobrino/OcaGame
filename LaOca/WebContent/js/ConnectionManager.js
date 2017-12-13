"use strict";
var ws= null;
var connected= false;
var nick="";
var flag =false;
var google_user=true;
var ConnectionManager={
	isConnected: function() {
		var request = new XMLHttpRequest();	
		request.open("get", "../isConnected.jsp");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.onreadystatechange=function() {
			if (request.readyState===4) {
				var respuesta=JSON.parse(request.responseText);
				if (respuesta.result==="OK") {
					connected = true;
					nick=respuesta.nick;
				}
				flag=true;
			}
		};
		request.send();	
	},
	logout: function(){
		var request = new XMLHttpRequest();	
		request.open("get", "../logout.jsp");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.onreadystatechange=function() {
			if (request.readyState===4) {
				var respuesta=JSON.parse(request.responseText);
				if (respuesta.result==="OK") {
					 location.href ="../index.html";
				}
			}
		};
		request.send();	
	},
	connectWebSocket: function(){
		ws=new WebSocket("ws://localhost:8080/LaOca/servidorDePartidas");
		
		ws.onopen = function() {
			addMensaje("Websocket conectado");
			alert("WebSocket connected");
		}	
		ws.onmessage = function(datos) {
			var mensaje=datos.data;
			mensaje=JSON.parse(mensaje);
			if (mensaje.tipo=="DIFUSION") {
				addMensaje(mensaje.mensaje);
			} else if (mensaje.tipo=="COMIENZO") {
				addMensaje("Comienza la partida");
				comenzar(mensaje);
			}
		}	 
	},
	logoutGoogle: function() {
		alert("desconectado google");
		ConnectionManager.logout();
		/*
	      var auth2 = gapi.auth2.getAuthInstance();
	      auth2.signOut().then(function () {
	        console.log('User signed out.');
	        ConnectionManager.logout();
	      });*/
    }
}
