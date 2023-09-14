package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

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

	public List<Categoria> listarConProductos() {
		List<Categoria> resultado = new ArrayList<>();
		
		String sqlQuery = "select c.id, c.nombre, p.id, p.nombre, p.cantidad from categoria c inner join producto p on c.id = p.categoria_id";
		
		try{
			final PreparedStatement statement = con.prepareStatement(sqlQuery);
		
			try (statement) {
				final ResultSet resultSet = statement.executeQuery();
				
				try(resultSet){
					while(resultSet.next()) {
						int categoriaId = resultSet.getInt("c.id");
						String categoriaNombre = resultSet.getString("c.nombre");
						
						var categoria = resultado
										.stream()
										.filter(cat -> cat.getId().equals(categoriaId))
										.findAny().orElseGet(()->{
											Categoria cat = new Categoria(categoriaId,categoriaNombre);
											resultado.add(cat);
											return cat;
										});
						var producto = new Producto(resultSet.getInt("p.id"),
								resultSet.getString("p.nombre"),
								resultSet.getInt("p.cantidad"));
						categoria.agregar(producto);
					}
				};
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return resultado;
	}

}
