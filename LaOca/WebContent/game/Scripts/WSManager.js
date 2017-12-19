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
				if (mensaje.type=="INFO_SALAS") {
					//TODO procesar el me saje y dibujar las salas
				} else{
					alert(mensaje.data);
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