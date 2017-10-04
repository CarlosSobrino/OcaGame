package edu.uclm.esi.tysweb.laoca.dominio;

public class Usuario {
	private String login;

	public Usuario(String nombreJugador) {
		this.login = nombreJugador;
	}
	
	public String getLogin() {
		return this.login;
	}
}
