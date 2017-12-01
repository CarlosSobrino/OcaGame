package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;
import java.io.IOException;
import javax.websocket.Session;
import org.json.JSONObject;

public class User {
	protected String email;
	private String pwd;
	protected Partida partida;
	private Session session;
	private Casilla casilla;
	private int turnosSinTirar;
	
	public User(String email) {
		this.email = email;
	}
	public User(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
	}
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public String getEmail() {
		return this.email;
	}
	public String getPwd() {
		return this.pwd;
	}
	public void SetEmail(String email) {
		this.email = email;
	}
	public void SetPwd(String email) {
		this.email = email;
	}
	
	public boolean insertIntoDB() throws Exception {
		return DAOUser.insert(this);
	}
	
	public boolean loginDB() throws Exception {
		return DAOUser.login(this);
	}
	
	public boolean changePasswordDB(String newPassword) throws Exception {
		if( DAOUser.changePassword(this, newPassword)){
			this.SetPwd(newPassword);
			return true;
		}
		return false;
	}
	
	public void setWSSession(Session sesion) {
		this.session=sesion;
	}
	
	public void setPartida(Partida partida) {
		this.partida=partida;
		if (partida!=null)
			partida.addJugador(this);
	}

	public void enviar(JSONObject jso) throws IOException {
		this.session.getBasicRemote().sendText(jso.toString());
	}

	public Casilla getCasilla() {
		return this.casilla;
	}
	public Partida getPartida() {
		return partida;
	}
	public Session getWSSession() {
		return session;
	}

	public void setCasilla(Casilla casilla) {
		this.casilla = casilla;
	}

	public int getTurnosSinTirar() {
		return this.turnosSinTirar;
	}
	
	public void setTurnosSinTirar(int turnosSinTirar) {
		this.turnosSinTirar = turnosSinTirar;
	}
	
	@Override
	public String toString() {
		return this.email + " jugando en " + (this.partida!=null ? this.partida.getId() : "ninguna ") + ", " + this.casilla.getPos() + ", turnos: " + this.turnosSinTirar;
	}
}
