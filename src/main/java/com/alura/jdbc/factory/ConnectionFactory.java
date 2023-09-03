package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public Connection recuperaConexion() throws SQLException{
		
		String bd = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC";
		String user = "root";
		String pass = "admin";
		
		return DriverManager.getConnection(bd, user, pass);
		
		
		
		
	}

}
