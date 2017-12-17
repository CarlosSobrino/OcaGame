var ws=null;
var WSManager={
		connect: function(){
			ws=new WebSocket("ws://localhost:8080/LaOca/WSServer");
			
			ws.onopen = function() {
				alert("WebSocket connected");
			}	
			
			ws.onerror = function() {
				alert("Ohhhh, se ha producido un error en la comunicación");
			}
			
			ws.onmessage = function(datos) {
				var mensaje=datos.data;
				mensaje=JSON.parse(mensaje);
				if (mensaje.type=="SALAS_LIST") {
					alert(mensaje.data);
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