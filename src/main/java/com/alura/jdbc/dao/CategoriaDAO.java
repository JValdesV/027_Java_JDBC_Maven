package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	
	final private Connection con;

	public CategoriaDAO(Connection conn) {
		this.con = conn;
	}

	public List<Categoria> listar() {
		
		List<Categoria> resultado = new ArrayList<>();
		
		String sqlQuery = "select * from categoria";
		
		try{
			final PreparedStatement statement = con.prepareStatement(sqlQuery);
		
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				
				try(resultSet){
					while(resultSet.next()) {
						var categoria = new Categoria(resultSet.getInt("ID"),resultSet.getString("nombre"));
						resultado.add(categoria);
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}

}
