package edu.uclm.esi.tysweb.laoca.persistencia;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.User;

public class DAOUser {
	private static boolean checkExistUser(String email, String password,MongoClient db_client) throws Exception {
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(email));
		if(password != null) criteria.append("pwd", new BsonString(password));
		MongoCollection<BsonDocument> users = db_client.getDatabase("LaOca").getCollection("users",BsonDocument.class);
		long user = users.count(criteria);
		if(user == 0) {
			return false;
		}else if(user == 1) {
			return true;
		}else {
			MongoBroker.get().close(db_client);
			throw new Exception("Fatal Error");
		}
	}
	
	public static boolean login(User user) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();

		if(!checkExistUser(user.getEmail(), user.getPwd(), db_client)) { //Si el usuario no existe
			MongoBroker.get().close(db_client);
			throw new Exception("Email o contraseña incorrectos");
		}
		MongoBroker.get().close(db_client);
		return true;
	}
	
	public static boolean insert(User user) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();
		//Check if the user exists before insert
		if(checkExistUser(user.getEmail(),null,db_client)) {
			MongoBroker.get().close(db_client);
			throw new Exception("Usuario ya registrado");
		}
		
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(user.getEmail()));
		criteria.append("pwd", new BsonString(user.getPwd()));
		
		MongoDatabase db = db_client.getDatabase("LaOca");
		MongoCollection<BsonDocument> users = db.getCollection("users",BsonDocument.class);
		users.insertOne(criteria);
		MongoBroker.get().close(db_client);
		return true;
	}
	//TODO test this method
	public static boolean changePassword(User user,String newPassword) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();
		//Check if the user exists before insert
		if(!checkExistUser(user.getEmail(),user.getPwd(),db_client)) {
			MongoBroker.get().close(db_client);
			throw new Exception("Email o contraseña incorrrecto");
		}
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(user.getEmail()));
		
		BsonDocument updateCirteria = new BsonDocument();
		updateCirteria.append("pwd", new BsonString(newPassword));
		
		
		MongoDatabase db = db_client.getDatabase("LaOca");
		MongoCollection<BsonDocument> users = db.getCollection("users",BsonDocument.class);
		users.findOneAndUpdate(criteria, updateCirteria);
		MongoBroker.get().close(db_client);
		return true;
	}
	
	
}
