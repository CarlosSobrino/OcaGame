package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Partida {
	private ConcurrentHashMap<String, User> jugadores;
	private int numeroDeJugadores;
	private int id;

	public Partida(User creador, int numeroDeJugadores){
		this.jugadores = new ConcurrentHashMap<>();
		this.jugadores.put(creador.getEmail(), creador);
		this.numeroDeJugadores = numeroDeJugadores;
		this.id = new Random().nextInt();
	}
	
	public Integer getId() {
		return this.id;
	}

	public void add(User user) {
		this.jugadores.put(user.getEmail(), user);
	}
	
	public boolean isReady() {
		return this.jugadores.size() == this.numeroDeJugadores;
	}
}
