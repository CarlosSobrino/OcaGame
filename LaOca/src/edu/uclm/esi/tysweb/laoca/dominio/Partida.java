package edu.uclm.esi.tysweb.laoca.dominio;

import java.util.Random;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uclm.esi.tysweb.laoca.websockets.WSServer;

public class Partida {
	private Vector<User> jugadores;
	private int numeroDeJugadores;
	private int id;
	private int jugadorConElTurno;
	private Tablero tablero;
	private User ganador;

	public Partida(User creador, int numeroDeJugadores) {
		this.jugadores=new Vector<>();
		this.jugadores.add(creador);
		this.numeroDeJugadores=numeroDeJugadores;
		this.id=new Random().nextInt();
		this.tablero=new Tablero();
	}

	public int getId() {
		return this.id;
	}

	public void add(User jugador) {
		this.jugadores.add(jugador);
	}

	public boolean isReady() {
		return this.jugadores.size()==this.numeroDeJugadores;
	}

	public void comenzar() {
		JSONObject jso=new JSONObject();
		jso.put("tipo", "COMIENZO");
		jso.put("idPartida", this.id);
		JSONArray jsa=new JSONArray();
		this.jugadorConElTurno=(new Random()).nextInt(this.jugadores.size());
		jso.put("jugadorConElTurno", getJugadorConElTurno().getEmail());
		for (User jugador : jugadores) 
			jsa.put(jugador.getEmail());
		jso.put("jugadores", jsa);
		
		broadcast(jso);
	}

	public User getJugadorConElTurno() {
		if (this.jugadores.size()==0)
			return null;
		return this.jugadores.get(this.jugadorConElTurno);
	}

	public JSONObject tirarDado(String nombreJugador, int dado) throws Exception {
		JSONObject result=new JSONObject();
		User jugador=findJugador(nombreJugador);
		if (jugador!=getJugadorConElTurno())
			throw new Exception("No tienes el turno");
		result.put("tipo", "TIRADA");
		result.put("casillaOrigen", jugador.getCasilla().getPos());
		result.put("dado", dado);
		Casilla destino=this.tablero.tirarDado(jugador, dado);
		result.put("destinoInicial", destino.getPos());
		Casilla siguiente=destino.getSiguiente();
		boolean conservarTurno=false;
		if (siguiente!=null) {
			conservarTurno=true;
			String mensaje=destino.getMensaje();
			result.put("destinoFinal", siguiente.getPos());
			result.put("mensaje", mensaje);
			this.tablero.moverAJugador(jugador, siguiente);
			if (siguiente.getPos()==62) { // Llegada
				this.ganador=jugador;
				result.put("ganador", this.ganador.getEmail());
			}
		}
		if (destino.getPos()==57) { // Muerte
			jugador.setPartida(null);
			result.put("mensaje", jugador.getEmail() + " cae en la muerte");
			this.jugadores.remove(jugador);
			this.jugadorConElTurno--;
			if (this.jugadores.size()==1) {
				this.ganador=this.jugadores.get(0);
				result.put("ganador", this.ganador.getEmail());
			}
		}
		if (destino.getPos()==62) { // Llegada
			this.ganador=jugador;
			result.put("ganador", this.ganador.getEmail());
		}
		int turnosSinTirar=destino.getTurnosSinTirar();
		if (turnosSinTirar>0) {
			result.put("mensajeAdicional", jugador.getEmail() + " está " + turnosSinTirar + " turnos sin tirar porque ha caído en ");
			jugador.setTurnosSinTirar(destino.getTurnosSinTirar());
		}
		result.put("jugadorConElTurno", pasarTurno(conservarTurno));
		return result;
	}

	private String pasarTurno(boolean conservarTurno) {
		if (!conservarTurno) {
			boolean pasado=false;
			do {
				this.jugadorConElTurno=(this.jugadorConElTurno+1) % this.jugadores.size();
				User jugador=getJugadorConElTurno();
				int turnosSinTirar=jugador.getTurnosSinTirar();
				if (turnosSinTirar>0) {
					jugador.setTurnosSinTirar(turnosSinTirar-1);
				} else
					pasado=true;
			} while (!pasado);
		}
		return getJugadorConElTurno().getEmail();
	}

	private User findJugador(String nombreJugador) {
		for (User jugador : jugadores)
			if (jugador.getEmail().equals(nombreJugador))
				return jugador;
		return null;
	}

	public void addJugador(User jugador) {
		this.tablero.addJugador(jugador);
	}
	
	void broadcast(JSONObject jso) {
		for (int i=jugadores.size()-1; i>=0; i--) {
			User jugador=jugadores.get(i);
			try {
				jugador.enviar(jso);
			}
			catch (Exception e) {
				// TODO: eliminar de la colección, mirar si la partida ha terminado
				// y decirle al WSServer que quite a este jugador
				this.jugadores.remove(jugador);
				WSServer.removeSession(jugador);
			}
		}
	}
	
	public Vector<User> getJugadores() {
		return jugadores;
	}

	public User getGanador() {
		return this.ganador;
	}

	public void terminar() {
		for (User jugador : this.jugadores)
			jugador.setPartida(null);
	}
	
	@Override
	public String toString() {
		String r="Partida " + id + "\n";
		for (User jugador : jugadores)
			r+="\t" + jugador + "\n";
		r+="\n";
		return r;
	}
}
