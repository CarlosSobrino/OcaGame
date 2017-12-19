"use strict";
var SalasManager={
			ShowSalasDiv: function (){
			    $("#div_salas").load("salas/salas.html");
			},
			OpenCreateSala: function (){
				$("#div_createsala").load("salas/createSala.html");
				document.getElementById("createSala_button").disabled = false;
			},
			CreateSala:function (){
				var sala_name = $("#createSala_input").val();
				//TODO Comprobar si la sala ya existe en el server si no:
				/*
				if(comprobar==true){
					enviar sala
				}else{
					$("#div_createSalaOpen").load("salas/salasError.html");
				}*/
				if(sala_name !== ""){
					SalasManager.SendNewSala(sala_name)
					$("#div_createSalaOpen").empty();
					$("#div_salas").empty();
				}	
			},
			SendNewSala: function (sala_name){
				WSManager.send("NEW_SALA",sala_name)
			},
			InsertSala: function (sala_name, players){
				var idSala= "idSala_"+sala_name;
				var idPlayers = idSala+"players";
				var idButton = idSala+"button";
				
				if (document.getElementById(idSala) !== null){
					//Actualizar tabla
					document.getElementById(idPlayers).innerHTML = players+"/4"; 
				}else{
					//Pintar nueva sala
					var inicio= '<tr>';
					var sala_table = '<td id='+idSala+'>'+ sala_name +'</td>';
					var players_table = '<td id='+idPlayers +'>'+players+'/4</td>';
					var join_table = '<td><button type="button" id="'+idButton+'" class="btn btn-primary">Entrar</button></td>';
					var fin= '</tr>';
					var injection = inicio+sala_table+players_table+join_table+fin;
					$("#salas_list").append(injection);
					document.getElementById(idButton).addEventListener("click", function() {
						SalasManager.OnClickJoinSala(sala_name);
					}, false);
				}
				if(players > 4){
					document.getElementById(idButton).disabled = true;
				}else{
					document.getElementById(idButton).disabled = false;
				}
			},
			OnClickJoinSala: function (sala_name){
			    WSManager.send("JOIN_SALA",sala_name);
				$("#div_salas").empty();
			},
}