package com.ens.kpi.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.inject.Inject;

public class DBConnection {
//	private static String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
//	private static String URL = "jdbc:derby:toursdb;create=true;shutdown=true";
	private static String DRIVER = "org.apache.derby.jdbc.ClientDriver";
	private static String URL = "jdbc:derby://localhost:1527/D:/derby-10.14.1.0/demo/databases/kpi;user=kpi";
	public static Connection conn = null;
    
//	@Inject
//	public dbConnection() {
//		super();
//		conn=getConnection();
//	}
	
	public Connection getConnection() 
    {
		//Singleton
		if (conn==null){
			// jdbc Connection
			try {
				Class.forName(DRIVER);
				conn = DriverManager.getConnection(URL); 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//Get a connection
				return conn;
			}
		} else {
			return conn;
		}
        
    }


}
