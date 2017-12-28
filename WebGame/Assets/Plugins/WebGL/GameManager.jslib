
mergeInto(LibraryManager.library, {

  LanzarDado: function () {
	GameManager.TirarDadoButton();
  },
  SendListo: function(){
	 WSManager.send("READY","");
  }
});
