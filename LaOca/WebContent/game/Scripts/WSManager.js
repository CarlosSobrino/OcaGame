var ws=null;
var WSManager={
		connect: function(){
			//TODO PROBAR SI FUNCIONA
			ws=new WebSocket("ws://"+window.location.hostname+":"+window.location.port+"/LaOca/WSServer");
			ws.onopen = function() {
				
			}
			ws.onerror = function() {
				location.href ="../index.html";
			}
			
			ws.onmessage = function(datos) {
				try {
					var mensaje=datos.data;
					console.debug(mensaje)
					mensaje=JSON.parse(mensaje);
					switch(mensaje.type) {
					case "INFO_SALAS":
						//{"data":{"salas":[{"players":1,"name":"diego"},{"players":1,"name":"pepe"}]},"type":"INFO_SALAS"}
						
						for(var i in mensaje.data.salas){
							SalasManager.InsertSala(mensaje.data.salas[i].name, mensaje.data.salas[i].players);
						}
						break;
					case "LOAD_GAME":
						GameManager.LoadGame();
						break;
					case "START_GAME":
						GameManager.StartGame();
						break;
					case "INFO_GAME":
						//{"data":{"players":[{"name":"admin1","player":1},{"name":"admin","player":1},{"name":"admin2","player":1},{"name":"admin3","player":1}]},"type":"INFO_GAME"}
						var players_info=[];
						for(var i in mensaje.data.players){
							players_info[mensaje.data.players[i].player] = mensaje.data.players[i].name;
						}
						GameManager.ActualizarInfoTablero(players_info[1],players_info[2],players_info[3],players_info[4]);
						break;
					case "MOVE_PLAYER_GAME":
						//{"data":{"player":1,"casilla":6},"type":"MOVE_PLAYER_GAME"}
						GameManager.MoveCasilla(mensaje.data.player,mensaje.data.casilla);
						break;
					case "TURNO_GAME":
						if(_player===mensaje.data.player){
							GameManager.PermitirDado();
							GameManager.ShowMsg("Tu turno");
						}else{
							GameManager.ShowMsg("Tu turno "+mensaje.data.name);
						}
	
						break;
					case "ROL_GAME":
						_player=mensaje.data.rol;
						break;
					case "MSG_GAME":
						GameManager.ShowMsg(mensaje.data.msg);
						break;
					case "WIN":
						gameInstance=null;
						 document.getElementById('gameContainer').innerHTML='¡Ha ganado '+mensaje.data.winner+'!';
						break;
					case "ABORT":
						gameInstance=null;
						 document.getElementById('gameContainer').innerHTML='La partida ha terminado porque '+mensaje.data.abort+' ha abandonado la partida :(';
						break;
					case "CHAT":
						printChat(mensaje.data.nick,mensaje.data.msg);
						break;
					}
				}
				catch(err) {
					
				}
			}
		},
		send: function(type,data){
			var mensaje = {
					type: type,
					data: data
			}
			ws.send(JSON.stringify(mensaje));
		}
}