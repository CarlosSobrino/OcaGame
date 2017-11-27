 $(document).ready(function(){
	 //Elementos del menu
	 var titulo = document.getElementsByTagName("title")[0].innerHTML;
	 //HTML
	 var inicio = '<nav class="navbar navbar-inverse"><div class="container-fluid">';
	 var tituloBar = '<div class="navbar-header"><a class="navbar-brand">'+titulo+'</a></div>';
	 var inicioBar =  '<ul class="nav navbar-nav"><li class="active"><a href="../index/index.html">Inicio</a></li></ul>';
	 var juegoBar =  '<ul class="nav navbar-nav"><li class="active"><a href="../game/game.html">Juego</a></li></ul>';
	 var rankingBar =  '<ul class="nav navbar-nav"><li class="active"><a href="../ranking/ranking.html">Ranking</a></li></ul>';
	 var inicioDerecha =  '<ul class="nav navbar-nav navbar-right">';
	 var registrarBar = '<li><a href="../registrar/registrar.html"><span class="glyphicon glyphicon-user"></span> Registrarse</a></li>';
	 var indetificarseBar = '<li><a href="../login/login.html"><span class="glyphicon glyphicon-log-in"></span> Identificarse</a></li>';
	 var finalDerecha = '</ul>';
	 var final = '</div></nav>'; 
	 
	 var finalMenu = inicio+
	 					tituloBar+
	 					inicioBar+
	 					juegoBar+
	 					rankingBar+
	 					inicioDerecha+
	 						registrarBar+
	 						indetificarseBar+
	 					finalDerecha+
	 				final;
	 $("#menu").append(finalMenu);
 });
 /*
 <nav class="navbar navbar-inverse"> 
			  <div class="container-fluid">
			    <div class="navbar-header">
			      <a class="navbar-brand">LaOca</a>
			    </div>
			    <ul class="nav navbar-nav">
			      <li class="active"><a href="game/game.html">Juego</a></li>
			    </ul>
			    <ul class="nav navbar-nav navbar-right">
			      <li><a href="registrar/registrar.html"><span class="glyphicon glyphicon-user"></span> Registrarse</a></li>
			      <li><a href="login/login.html"><span class="glyphicon glyphicon-log-in"></span> Identificarse</a></li>
			    </ul>
			  </div>
			</nav> 
 
 
 */