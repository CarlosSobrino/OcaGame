"use strict";
$(document).ready(function(){
	 var gameInstance = UnityLoader.instantiate("gameContainer", "Build/App.json", {onProgress: UnityProgress});
});


var GameManager= {
		MoveFicha:function(player,dado){
			player = 1;
			dado=1;
			
			window.alert("Mover "+player+" "+dado+" casillas");
			gameInstance.SendMessage ('GameManager', 'MovePlayer', "player ~ dado");
		}
}