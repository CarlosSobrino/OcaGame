package edu.uclm.esi.tysweb.laoca.persistencia;

import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import edu.uclm.esi.tysweb.laoca.dominio.User;
import edu.uclm.esi.tysweb.laoca.dominio.UserRegistered;

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
	
	private static boolean checkExistNick(String nick,MongoClient db_client) throws Exception {
		BsonDocument criteria = new BsonDocument();
		criteria.append("nick", new BsonString(nick));
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
	
	public static boolean checkNick(String nick) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();
		boolean aux = checkExistNick(nick, db_client);
		MongoBroker.get().close(db_client);
		return aux;
	}
	
	
	public static User login(String email,String pwd) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();

		if(!checkExistUser(email, pwd, db_client)) { //Si el usuario no existe
			MongoBroker.get().close(db_client);
			throw new Exception("Email o contraseña incorrectos");
		}
		UserRegistered user = new UserRegistered(email,pwd);
		user.setNick(getNick(email, db_client));
		user.setScore(getScore(email, db_client));
		MongoBroker.get().close(db_client);
		return user;
	}
	//TODO test this method
	public static User insert(String email,String pwd,String nick) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();
		//Check if the user exists before insert
		if(checkExistUser(email,null,db_client)) {
			MongoBroker.get().close(db_client);
			throw new Exception("Usuario ya registrado");
		}
		UserRegistered user = new UserRegistered(email,pwd);
		if(checkExistNick(nick, db_client)) {
			MongoBroker.get().close(db_client);
			throw new Exception("Este nick ya existe, elige otro");
		}
		user.setNick(nick);
		user.setScore(0);
		
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(email));
		criteria.append("pwd", new BsonString(pwd));
		criteria.append("nick", new BsonString(nick));
		criteria.append("score", new BsonInt32(0));
		
		MongoDatabase db = db_client.getDatabase("LaOca");
		MongoCollection<BsonDocument> users = db.getCollection("users",BsonDocument.class);
		users.insertOne(criteria);
		MongoBroker.get().close(db_client);
		return user;
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
	//TODO test this method
	public static boolean changeScore(String email,int score) throws Exception{
		MongoClient db_client =  MongoBroker.get().getBD();
		//Check if the user exists before insert
		if(!checkExistUser(email,null,db_client)) {
			MongoBroker.get().close(db_client);
			throw new Exception("Error al actualizar puntuación, email incorrecto");
		}
		
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(email));
		
		BsonDocument updateCirteria = new BsonDocument();
		updateCirteria.append("score", new BsonInt32(score));
		
		
		MongoDatabase db = db_client.getDatabase("LaOca");
		MongoCollection<BsonDocument> users = db.getCollection("users",BsonDocument.class);
		users.findOneAndUpdate(criteria, updateCirteria);
		MongoBroker.get().close(db_client);
		return true;
	}
	
	private static int getScore(String email,MongoClient db_client) throws Exception {
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(email));
		MongoCollection<BsonDocument> users = db_client.getDatabase("LaOca").getCollection("users",BsonDocument.class);
		BsonDocument find= users.find(criteria).first();
		return find.getInt32("score").getValue();
	}
	
	private static String getNick(String email,MongoClient db_client) throws Exception {
		BsonDocument criteria = new BsonDocument();
		criteria.append("email", new BsonString(email));
		MongoCollection<BsonDocument> users = db_client.getDatabase("LaOca").getCollection("users",BsonDocument.class);
		BsonDocument find= users.find(criteria).first();
		return find.getString("nick").getValue();
	}
	//TODO Falta hacer un sort cogiendo el mayor score y poniendo un limit en la consulta
	public static JSONObject getScoreList() throws Exception{
		JSONArray score_array = new JSONArray();
		JSONObject score_obj = new JSONObject();
		MongoClient db_client =  MongoBroker.get().getBD();
		try {
			Bson sort = Sorts.descending("users", "score");
			MongoCollection<BsonDocument> users = db_client.getDatabase("LaOca").getCollection("users",BsonDocument.class);
			FindIterable<BsonDocument> find= users.find().sort(sort).limit(50);
			int pos=0;
			for(BsonDocument index : find) {
				pos++;
				System.out.println(index.getString("nick").getValue());
				score_obj.put("nick", index.getString("nick").getValue());
				score_obj.put("score", index.getInt32("score").getValue());
				score_obj.put("position", pos+"");
				score_array.put(score_obj);
				score_obj = new JSONObject();
			}
			JSONObject result = new JSONObject();

			result.put("scores", score_array);
			MongoBroker.get().close(db_client);
			return result;
		}catch(Exception e) {
			MongoBroker.get().close(db_client);
			throw e;
		}

	}
}
