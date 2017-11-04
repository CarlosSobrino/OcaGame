function validate(){
	var login = {
		user : document.getElementById("user").value,
		pass : document.getElementById("pass").value
	}
	var request = new XMLHttpRequest();
	request.open("post", "login/login.jsp");
	request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	request.onreadystatechange = function() {
		if(request.readyState == 4){
			var respuesta = JSON.parse(request.responseText);
			console.log(respuesta.result);
			conectarWebSocket();
		}
	};
	request.send("login="+JSON.stringify(login)); // {"user":"asdda@gasg","pass":"asd"}
}