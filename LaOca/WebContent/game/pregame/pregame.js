"use strict";
$(document).ready(function(){
	$("#div_pregame").load("pregame/pregame.html");
});
var Pregame={
		jugarSinRegistrar:function(){
			var user = {
					nick : document.getElementById("nick").value,
				}
			if(nick === ""){
				alert("No se ha escrito ningun nick");
			}else{
				var request = new XMLHttpRequest();
				request.open("post", "pregame/pregame.jsp");
				request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				request.onreadystatechange = function() {
					if(request.readyState === 4){
						var respuesta = JSON.parse(request.responseText);
						if(respuesta.result === "OK"){
							alert(respuesta.mensaje);
						}else{
							alert(respuesta.mensaje);
						}
						//conectarWebSocket();
					}
				};
				request.send("user="+JSON.stringify(user));
			}
		}
}

