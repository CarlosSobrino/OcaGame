package edu.uclm.esi.tysweb.laoca.presistencia;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoBroker {
	public MongoClient mongoClient;
	
	private MongoBroker() {
		this.mongoClient = new MongoClient("alarcosj.esi.uclm.es",27017);
	}
	private static class MongoBrokeHolder{
		static MongoBroker singelton = new MongoBroker();
	}
	public static MongoBroker get() {
		return MongoBrokeHolder.singelton;
	}
	public static void main(String [] args) {
		MongoBroker broker = MongoBroker.get();
		MongoDatabase db = broker.mongoClient.getDatabase("DIEGO");
		
		if(db.getCollection("usuarios")==null) {
			db.createCollection("usuarios");
		}
		MongoCollection<BsonDocument> users = db.getCollection("usuarios",BsonDocument.class);
		for(int i=0; i<=100;i++) {
			BsonDocument user = new BsonDocument();
			user.put("email", new BsonString("user"+i+"@user.com"));
			user.put("pwd", new BsonString("user"));
			users.insertOne(user);
		}
	BsonDocument criterioBusqueda = new BsonDocument();
	criterioBusqueda.append("email",new BsonString("user100@user.com"));
	FindIterable<BsonDocument> busqueda = users.find(criterioBusqueda);
	BsonDocument elementoBuscado = busqueda.first();
	System.out.println(elementoBuscado.getString("email"));
		
		
	}
}
