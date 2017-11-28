package edu.uclm.esi.tysweb.laoca.persistencia;

import org.bson.BsonDocument;
import org.bson.BsonString;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import edu.uclm.esi.tysweb.laoca.dominio.User;

public class DAOUsuario {
	public static boolean existe(String nombreJugador) throws Exception {
		MongoBroker broker = MongoBroker.get();
		BsonDocument criterio = new BsonDocument();
		criterio.append("email", new BsonString(nombreJugador));
		
		MongoDatabase db = broker.getBD().getDatabase("LaOca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios",BsonDocument.class);
		BsonDocument usuario = usuarios.find(criterio).first();
		//broker.close();
		return usuario != null;
	}

	public static void insert(String email, String pwd) throws Exception {
		BsonDocument bUser = new BsonDocument();
		bUser.append("email", new BsonString(email));
		bUser.append("pwd", new BsonString(pwd));
		MongoDatabase db = MongoBroker.get().getBD().getDatabase("LaOca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios",BsonDocument.class);
		usuarios.insertOne(bUser);
	}
	public static boolean existeUser(User user) throws Exception{
		//TODO Cearrar la conexion a la pool
		BsonDocument bUser = new BsonDocument();
		bUser.append("email", new BsonString(user.getEmail()));
		bUser.append("pwd", new BsonString(user.getPwd()));
		MongoDatabase db = MongoBroker.get().getBD().getDatabase("LaOca");
		MongoCollection<BsonDocument> usuarios = db.getCollection("usuarios",BsonDocument.class);
		return !usuarios.find(bUser).first().isNull();	
		}
}
