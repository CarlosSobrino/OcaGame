var ws=null;
var WSManager={
		connect: function(){
			ws=new WebSocket("ws://localhost:8080/LaOca/WSServer");
			
			ws.onopen = function() {
				
			}	
			
			ws.onerror = function() {
				location.href ="../index.html";
			}
			
			ws.onmessage = function(datos) {
				var mensaje=datos.data;
				mensaje=JSON.parse(mensaje);
				switch(mensaje.type) {
				case "INFO_SALAS":
					//{"data":{"salas":[{"players":1,"name":"diego"},{"players":1,"name":"pepe"}]},"type":"INFO_SALAS"}
					for(var i in mensaje.data.salas){
						SalasManager.InsertSala(mensaje.data.salas[i].name, mensaje.data.salas[i].players);
					}
					break;
				case "LOAD_GAME":
					alert("game");
					GameManager.LoadGame();
					break;
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