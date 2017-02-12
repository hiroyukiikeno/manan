package com.manandakana.dao;

public class DAOProperties {
	private String databaseType;
	private String datasourceReference;
	public DAOProperties(){
		databaseType = "RDB";
		datasourceReference = "jdbc/dashDB for Analytics-k1";
	}
	public String getDatabaseType(){
		return databaseType;
	}
	public String getDatasourceReference(){
		return datasourceReference;
	}

}
