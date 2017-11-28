package edu.uclm.esi.tysweb.laoca.persistencia;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class Pool {
	private ConcurrentLinkedQueue<MongoClient> libres;
	private ConcurrentLinkedQueue<MongoClient> usadas;
	private int peticionesServidas=0;
	
	public Pool(int numeroDeConexiones) {
		try {

			String url="localhost";
			int port = 27017;
			ServerAddress address=new ServerAddress(url);
			
			this.libres=new ConcurrentLinkedQueue<>();
			this.usadas=new ConcurrentLinkedQueue<>();
			for (int i=0; i<numeroDeConexiones; i++) {
				MongoClient bd;
				bd = new MongoClient(url, port);
				this.libres.add(bd);
				System.out.println("Conexion establecida: " + i);
			}
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	public MongoClient getBD() throws Exception {
		if (this.libres.size()==0)
			throw new Exception("No hay conexiones libres");
		MongoClient bd=this.libres.poll();
		this.usadas.offer(bd);
		System.out.println("Libres/Ocupadas/Servidas: " + this.libres.size() + "/" + this.usadas.size() + "/" + (++peticionesServidas));
		return bd;
	}

	public void close(MongoClient bd) {
		this.usadas.remove(bd);
		this.libres.offer((MongoClient) bd);
		System.out.println("Libres/Ocupadas: " + this.libres.size() + "/" + this.usadas.size());
	}
}
