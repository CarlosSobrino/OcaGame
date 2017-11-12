var GameManager= {
		MoveFicha:function(player,dado){
			player = 1;
			dado=1;
			
			window.alert("Mover "+player+" "+dado+" casillas");
			gameInstance.SendMessage ('GameManager', 'MovePlayer', "player ~ dado");
		}
}