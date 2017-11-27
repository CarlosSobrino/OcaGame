"use strict";
var GameManager= {
		MoveFicha:function(player,dado){
			player = 1;
			dado=1;
			data = player +"\n"+dado
			window.alert(data);
			gameInstance.SendMessage ('GameManager', 'MovePlayerArray', data);
		}
}
