package edu.uclm.esi.tysweb.laoca.dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.uclm.esi.tysweb.laoca.dao.BrokerConPool;
import edu.uclm.esi.tysweb.laoca.dao.BrokerSinPoolAbriendoYCerrando;

public class User {
	private String email;
	private String pass;

	public User(String email) {
		this.email = email;
	}
	public User(String email, String pass) {
		this.email = email;
		this.pass = pass;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public boolean existeConPool(String pwd) throws Exception {
		String sql="Select count(*) from usuario where email=? and pwd=?";
		Connection bd=BrokerConPool.get().getBD();
		PreparedStatement ps=bd.prepareStatement(sql);
		ps.setString(1, this.email);
		ps.setString(2, pwd);
		ResultSet rs=ps.executeQuery();
		rs.next();
		int resultado=rs.getInt(1);
		BrokerConPool.get().close(bd);
		return resultado==1;
	}

	public boolean existeAbriendoYCerrando(String pwd) throws SQLException {
		String sql="Select count(*) from usuario where email=? and pwd=?";
		Connection bd=BrokerSinPoolAbriendoYCerrando.get().getBD();
		PreparedStatement ps=bd.prepareStatement(sql);
		ps.setString(1, this.email);
		ps.setString(2, pwd);
		ResultSet rs=ps.executeQuery();
		rs.next();
		int resultado=rs.getInt(1);
		BrokerSinPoolAbriendoYCerrando.get().close(bd);
		return resultado==1;
	}
}
