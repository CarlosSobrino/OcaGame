"use strict";
var gameInstance = null;
var _player=1;
var _permitirDado = false;
var GameManager= {
		MoveFicha:function(in_player,dado){
			var data = in_player +"\n"+dado
			gameInstance.SendMessage ('GameManager', 'MovePlayer', data);
		},
		StartGame:function(){
			gameInstance.SendMessage ('GameManager', 'StartGame');
		},
		PermitirDado:function(){
			_permitirDado = true;
			gameInstance.SendMessage ('GameManager', 'PermitirDado');
		},
		ActualizarInfoTablero:function(player1,player2,player3,player4){
			var data = player1 +"\n"+player2 +"\n"+player3 +"\n"+player4 +"\n";
			gameInstance.SendMessage ('GameManager', 'InformacionTablero',data);
		},
		LoadGame:function(){
		      var insert_div='<div id="gameContainer" style="width: 960px; height: 600px"></div>';
		      $("#div_game").append(insert_div);
			  if(gameInstance === null){
				  gameInstance=UnityLoader.instantiate("gameContainer", "Build/App.json",{onProgress: UnityProgress});
			  }
			  openChat();
		},
		TirarDadoNumero:function(dado){
			//GameManager.GirarDado();
			setTimeout(function(){
				var aleatorio = dado;
				WSManager.send("DADO_GAME",aleatorio);
				gameInstance.SendMessage ('GameManager', 'LanzarDadoPlayer',aleatorio);
				_permitirDado = false;
			}, 1300);
		},
		MoveCasilla:function(in_player,casilla){
			var data = in_player +"\n"+casilla
			setTimeout(function(){
				gameInstance.SendMessage ('GameManager', 'MoveCasillaPlayer',data);
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
			//var tiradas = ['4', '3', '3', '1', '1', '4', '4', '1', '5', '3', '2', '1', '4', '5', '5', '5', '1', '6', '1', '2', '4', '1'];
			GameManager.GirarDado();
			setTimeout(function(){
				var aleatorio = randomIntFromInterval(1,6);
				WSManager.send("DADO_GAME",aleatorio);
				gameInstance.SendMessage ('GameManager', 'LanzarDadoPlayer',aleatorio);
				_permitirDado = false;
			}, 1300);

		},
		GirarDado:function(){
			gameInstance.SendMessage ('GameManager', 'GirarDado');
		},
		ShowMsg:function(msg){
			gameInstance.SendMessage ('GameManager', 'ShowMsg',msg);
		},
};

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
};

function randomIntFromInterval(min,max)
{
    return Math.floor(Math.random()*(max-min+1)+min);
}



