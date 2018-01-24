"use strict";
$(document).ready(function(){
	setTimeout(function() {
	    if(flag === "false"){
	    	
	    }else{
	    	if(connected === false){
	    		$("#div_pregame").load("pregame/pregame.html");
	    	}else{
	    		WSManager.connect();
	    		SalasManager.ShowSalasDiv();
	    	}
	    }   
	}, 1000);
});
function onClickNickPregame(){
	nick = $("#nick_input").val();
	sendNick(nick);
}

function sendNick(nick){
	var user = {
		nick: nick
	}
	var request = new XMLHttpRequest();
	request.open("post", "pregame/pregame.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState === 4){
			var respuesta = JSON.parse(request.responseText);
			if(respuesta.result ==="OK"){
				WSManager.connect();
				remove("div_pregame");
				SalasManager.ShowSalasDiv();
			}else{
				//TODO Reflejar el mensaje en rojo debajo del email
				alert(respuesta.mensaje);
			}
		}
	};
	request.send("user="+JSON.stringify(user));
}

function remove(id) {
    var elem = document.getElementById(id);
    return elem.parentNode.removeChild(elem);
}

