package com.manandakana.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DAOBase implements DAO {

	@Override
	public Connection getConnection() throws DAOException {
		Connection connection = null;
		Context context = null;
		DataSource datasource = null;
		DAOProperties daoProperties = new DAOProperties();
		try {
			context = new InitialContext();
			datasource = (DataSource)context.lookup(daoProperties.getDatasourceReference());
		} catch (NamingException ne){
			System.err.println("failed to lookup jdbc datasource : " + daoProperties.getDatasourceReference());
			ne.printStackTrace();
		}
		if (datasource != null){
			try {
				connection = datasource.getConnection();
			} catch (SQLException se){
				System.err.println("failed to get connection");
				throw new DAOException((Throwable)se);
			}
		} else {
			System.err.println("the datasource is null");
			throw new DAOException("the datasource is null");
		}
		return connection;
	}
	
	public Timestamp getCurrentTimestamp(){
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts;
	}

}
