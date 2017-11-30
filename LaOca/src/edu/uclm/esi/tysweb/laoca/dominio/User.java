package edu.uclm.esi.tysweb.laoca.dominio;

import edu.uclm.esi.tysweb.laoca.persistencia.DAOUser;

public class User {
	private String email;
	private String pwd;

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
}
