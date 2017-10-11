function Tablero(){
	this.casillas=[];
	crearCasillas();
}

// 350 de alto, 450 de ancho, a 50,50 por casilla
//5,9,14,18,23,27,32,36,41,45,54,59 --> casillas tipo oca
Tablero.prototype.crearCasillas = function(){
	for(var i=0;i<63;i++){
		var casilla = new Casilla();
		this.casillas.push(casilla);
	}
	var ocas = [4,8,13,17,22,26,31,35,40,44,49,53,58];
	for(var i=0;i<ocas.length;i++){
		this.casillas[ocas[i]].tipo="OCA";
	}
	for(var i=0;i<9;i++){
		this.casilla[i].x0=i*50;
		this.casillas[i].y0=350-50;
	    this.casillas[i].xF = this.casillas[i].x0+50;
	    this.casillas[i].yF = this.casillas[i].y0+50;
	    this.casillas[i].crearCirculo();
	}
	
	
}

function Casilla(){
	this.tipo="NORMAL";
}

Casilla.prototype.crearCirculo = fuction (){
	this.rectangulo = document.createElementNS("https://www.w3.org/2000/svg","rectangle");
}
