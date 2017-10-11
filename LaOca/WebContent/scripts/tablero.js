function Tablero(){
	this.casillas=[];
	crearCasillas();
}
Tablero.prototype.dibujar = function(lienzo){
	 for(var i=0; i<9; i++){
	  this.casillas[i].dibujar(lienzo);
	}
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

Casilla.prototype.crearCirculo = function (){
	this.rectangulo = document.createElementNS("https://www.w3.org/2000/svg","rect");
	this.rectangulo.setAttributeNS("x",this.x0);
	this.rectangulo.setAttributeNS("y",this.y0);
	this.rectangulo.setAttributeNS("width", 50);
	this.rectangulo.setAttributeNS("height", 50);
	this.rectangulo.setAttributeNS("stroke","green");
	this.rectangulo.setAttributeNS("stroke-width","4");
	this.rectangulo.setAttributeNS("fill","rgb(100,100,100)");
}

Casilla.prototype.crearCirculo = function (){
	
}

Casilla.prototype.dibujar = function (){
	lienzo.appendChild(this.rectangulo);
}
