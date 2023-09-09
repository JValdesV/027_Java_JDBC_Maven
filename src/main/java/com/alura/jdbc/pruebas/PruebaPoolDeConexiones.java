package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaPoolDeConexiones {

	public static void main(String[] args) throws SQLException {
		//Se crea un objeto con de tipo conectionfactory que es el encargado de permitir el acceso a la base de datos
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//Se crean 20 instancias para testear el pool asignado que son 10 coneiones
		//Aplicar un "show processlit;" en mysql para listar las conexiones actuales a la bd
		for(int i=0; i<20; i++) {
			Connection con = connectionFactory.recuperaConexion();
			
			System.out.println("Abriendo la conexion numero "+(i+1));
		}
		
		
	}

}
