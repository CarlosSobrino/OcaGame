"use strict";
var gameInstance = null;
var _player=1;
var GameManager= {
		MoveFicha:function(in_player,dado){
			var data = in_player +"\n"+dado
			gameInstance.SendMessage ('GameManager', 'MovePlayer', data);
		},
		PermitirDado:function(){
			gameInstance.SendMessage ('GameManager', 'PermitirDado');
		},
		ActualizarInfoTablero(player1,player2,player3,player4){
			var data = player1 +"\n"+player2 +"\n"+player3 +"\n"+player4 +"\n";
			gameInstance.SendMessage ('GameManager', 'InformacionTablero',data);
		},
		LoadGame:function(){
			  if(gameInstance === null){
				  gameInstance=UnityLoader.instantiate("gameContainer", "Build/App.json", {onProgress: UnityProgress});
			  }
		},
		TirarDadoNumero:function(in_player,dado){
			GameManager.GirarDado();
			setTimeout(function(){
				gameInstance.SendMessage ('GameManager', 'LanzarDadoPlayer',dado);
				GameManager.MoveFicha(in_player, dado);
			},1300);
		},
		TirarDadoRandom:function(in_player){
			GameManager.GirarDado();
			setTimeout(function(){
				var aleatorio = Math.round(Math.random()*6);
				gameInstance.SendMessage ('GameManager', 'LanzarDadoPlayer',aleatorio);
				GameManager.MoveFicha(in_player, aleatorio);
			},1300);
		},
		TirarDadoButton:function(){
			GameManager.GirarDado();
			setTimeout(function(){
				var aleatorio = Math.round(Math.random()*6);
				gameInstance.SendMessage ('GameManager', 'LanzarDadoPlayer',aleatorio);
				GameManager.MoveFicha(_player, aleatorio);
			}, 1300);
		},
		GirarDado:function(){
			gameInstance.SendMessage ('GameManager', 'GirarDado');
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

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
};



