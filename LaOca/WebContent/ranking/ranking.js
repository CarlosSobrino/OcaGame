"use strict";
$(document).ready(function(){
	var request = new XMLHttpRequest();	
	request.open("get", "ranking.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange=function() {
		if (request.readyState==4) {
			var respuesta=JSON.parse(request.responseText);
			if (respuesta.result==="OK") {
				for(var i in respuesta.ranking.scores){
					var inicio= '<tr>';
					var pos = '<td>'+ respuesta.ranking.scores[i].position +'</td>';
					var nick = '<td>'+respuesta.ranking.scores[i].nick+'</td>';
					var score = '<td>'+respuesta.ranking.scores[i].score+'</td>';
					var fin= '</tr>';
					var injection = inicio+pos+nick+score+fin;
					$("#ranking_list").append(injection);
				}
			}else{
				alert("Error, no se pudo cargar la lista de ranking");
			}
		}
	};
	request.send();	
});
