package WorldCupTimeline.src.database;

import java.sql.*;
import java.util.Properties;

public class DBConnection {
	
	/* Declaring:
	 	* username and password for access;
	 	* mysql or derby for Database Management System;
	 	* localhost or IP-address for access-type;
	 	* which port the server is on; and
	 	* the the name for the database.
	 */
	private static final Object password = "password";
	private static final Object userName = "mw223ij";
	private static final String dbms = "mysql";
	private static final String serverName = "localhost";
	private static final String portnumber = "3306";
	private static final String dBName = "timeline_db";

	public Connection getConnection() throws SQLException{
		// Creates a connection set to null
		Connection conn = null;
		
		// Creates properties for username and password for access
		Properties connectionProps = new Properties();
		connectionProps.put("user", DBConnection.userName);
		connectionProps.put("password", DBConnection.password);
		
		// Sets the connection to either mysql or Java's built in derby
		// with correct URL and properties
		if(DBConnection.dbms.equals("mysql")) {
			conn = DriverManager.getConnection("jdbc:" + DBConnection.dbms + "://" + DBConnection.serverName + ":" + DBConnection.portnumber + "/" + DBConnection.dBName, connectionProps);
		}
		if(DBConnection.dbms.equals("derby")) {
			conn = DriverManager.getConnection("jdbc:" + DBConnection.dbms + ":" + DBConnection.dBName + ";create=true", connectionProps);
		}
		
		// Returns the connection when made
		return conn;
	}
}
