function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  console.log('Name: ' + profile.getName());
  console.log('Image URL: ' + profile.getImageUrl());
  console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
  var login = {
			googleId : profile.getId(),
			email: profile.getEmail(),
			nick: profile.getName()
		}
		var request = new XMLHttpRequest();
		request.open("post", "loginGoogle.jsp");
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.onreadystatechange = function() {
			if(request.readyState === 4){
				var respuesta = JSON.parse(request.responseText);
				if(respuesta.result ==="OK"){
					 location.href ="../index.html";
				}else{
					//TODO Reflejar el mensaje en rojo debajo del email
					//alert(respuesta.mensaje);
				}
			}
		};
		request.send("login="+JSON.stringify(login));
};
