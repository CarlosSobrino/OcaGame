package edu.uclm.esi.tysweb.laoca.dao;

import java.sql.Connection;

public class BrokerConPool {
	private Pool pool;
	
	private BrokerConPool() {
		this.pool=new Pool(10);
	}
	
	private static class BrokerHolder {
		static BrokerConPool singleton=new BrokerConPool();
	}
	
	public static BrokerConPool get() {
		return BrokerHolder.singleton;
	}

	public Connection getBD() throws Exception {
		return this.pool.getBD();
	}

	public void close(Connection bd) {
		this.pool.close(bd);
	}
}
