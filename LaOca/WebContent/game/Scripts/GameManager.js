"use strict";
var gameInstance = null;
var GameManager= {
		MoveFicha:function(player,dado){
			data = player +"\n"+dado
			gameInstance.SendMessage ('GameManager', 'MovePlayer', data);
		},
		PermitirDado:function(){
			gameInstance.SendMessage ('GameManager', 'PermitirDado');
		},
		ActualizarInfoTablero(player1,player2,player3,player4){
			data = player1 +"\n"+player2 +"\n"+player3 +"\n"+player4 +"\n";
			gameInstance.SendMessage ('GameManager', 'InformacionTablero',data);
		},
		LoadGame:function(){
			  if(gameInstance === null){
				  gameInstance=UnityLoader.instantiate("gameContainer", "Build/App.json", {onProgress: UnityProgress});
			  }
		},
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
						//TODO conectarWebSocket();
					}
				};
				request.send("user="+JSON.stringify(user));
			}
		}
};
//FROM UNITY TO JS
Assets/Plugins/WebGL/MyPlugin.jslib
mergeInto(LibraryManager.library, {

  LanzarDado: function () {
    window.alert("Hello, world!");
    //TODO crear metodo que genere aleatoriamente un numero del 1 al 6
  }
});


