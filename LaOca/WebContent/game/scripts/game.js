function init() {
	var app = new PIXI.Application(800, 600, {backgroundColor : 0x1099bb}); //transparent: true
	document.getElementById('game-container').appendChild(app.view);
	var tablero = PIXI.Sprite.fromImage('assets/resources/tablero.jpg');
	var scale = 2;

	// center the sprite's anchor point
	tablero.anchor.set(0.5);
	// move the sprite to the center of the screen
	tablero.width = app.renderer.width;
	tablero.height = app.renderer.height;
	
	tablero.x = app.renderer.width / 2;
	tablero.y = app.renderer.height / 2;
    //tablero.scale= scale;
    
    app.stage.addChild(tablero);

}

