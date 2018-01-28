package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;
import java.io.IOException;
import javax.websocket.Session;
import org.json.JSONObject;

public class User {
	protected String email;
	private String pwd;
	private int score;
	private String nick;
	protected Sala sala;
	private Session session;
	private Casilla casilla;
	private int turnosSinTirar;
	private boolean googleUser;
	public enum StateUser {
	    WAITING_SALA, CONNECTED, DISCONECTED, INSIDE_SALA,
	    PLAYING,READY
	}
	private StateUser state;
	
	
	public StateUser getState() {
		return state;
	}
	public void setState(StateUser state) {
		this.state = state;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
		if(email != null)
			try {
				DAOUser.changeScore(this.email, score);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}

	
	public User(String email) {
		this.email = email;
		this.state = StateUser.CONNECTED;
		this.googleUser =false;
	}
	public User(String email, String pwd) {
		this.email = email;
		this.pwd = pwd;
		this.state = StateUser.CONNECTED;
		this.googleUser = false;
	}
	 public void googleUser() {
		 this.googleUser = true;
	 }
	 public boolean getGoogle() {
		 return this.googleUser;
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
	public void SetPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public boolean updateScoreDB(int newScore) throws Exception {
		int old_score = this.getScore();
		if(newScore < old_score) return false;
		DAOUser.changeScore(this.email,score);
		this.score=newScore;
		return true;
	}
	public boolean changePasswordDB(String old_pwd,String newPassword) throws Exception {
		if(!old_pwd.equals(pwd)) {
			return false;
		}
		if( DAOUser.changePassword(this, newPassword)){
			this.SetPwd(newPassword);
			return true;
		}
		return false;
	}
	
	public void setWSSession(Session sesion) {
		this.session=sesion;
	}
	
	public void setSala(Sala sala) {
		this.sala=sala;
		if (sala!=null)
			sala.addJugador(this);
	}

	public void enviar(JSONObject jso) throws IOException {
		this.session.getBasicRemote().sendText(jso.toString());
	}

	public Casilla getCasilla() {
		return this.casilla;
	}
	public Sala getSala() {
		return sala;
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
		return this.email + " jugando en " + (this.sala!=null ? this.sala.getName() : "ninguna ") + ", " + this.casilla.getPos() + ", turnos: " + this.turnosSinTirar;
	}
	public void exitSala() {
		this.sala=null;
		this.casilla=null;
		this.state=null;
		this.turnosSinTirar=0;
		this.state = StateUser.CONNECTED;
	}
}
