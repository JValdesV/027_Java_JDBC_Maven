package com.alura.jdbc.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	public void guardar(Producto producto) {
		
		System.out.println("DAO: "+producto.toString());
    	
		final Connection con = new ConnectionFactory().recuperaConexion();
		
    	try(con){
    		System.out.println(con.isClosed());
    		final PreparedStatement statement = con.prepareStatement("insert into producto(nombre, descripcion, cantidad, categoria_id)" 
    				+ " values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS); 
    		//Englobamos las instrucciones que son potenciales de una exception para trabajar con transacciones
    		try(statement) {
    			 ejecutaRegistro(producto, statement);
    		}
    	}
		 catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
				
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
		statement.setInt(4,  producto.getCategoriaId());
		
		statement.execute();
		
		final ResultSet resultset = statement.getGeneratedKeys();
    	try(resultset) {
    		while(resultset.next()) {
    			producto.setId(resultset.getInt(1));
    			System.out.println(producto.toString());
    		}
    	}
		
	}

	public int eliminar(Integer id) {
		
		int resultado = 0;
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try(con){
			
			String sqlQuery = "DELETE FROM PRODUCTO WHERE ID = ?";
			
			PreparedStatement statement = con.prepareStatement(sqlQuery);
			try(statement){
				
				statement.setInt(1, id);
				statement.executeUpdate();
				resultado = statement.getUpdateCount();
				
				return resultado;
				
			}
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	public List<Producto> listar() {
		
		List<Producto> resultado = new ArrayList<>(); 
		
		final Connection con = new ConnectionFactory().recuperaConexion();
	
		
		try(con){
			
			final Statement statement = con.createStatement();
			
			try(statement){
				
				String sql = "select * from producto";
				
				Boolean result = statement.execute(sql);
				
				ResultSet resultSet = statement.getResultSet();
				
				
				while(resultSet.next()) {
					Producto fila = new Producto();
					fila.setId(resultSet.getInt(1));
					fila.setNombre(resultSet.getString(2));
					fila.setDescripcion(resultSet.getString(3));
					fila.setCantidad(resultSet.getInt(4));
					
					resultado.add(fila);
				}
			
				return resultado;
			}
			
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	

	public int modificar(Producto producto) {
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		
		try {
	        final PreparedStatement statement = con.prepareStatement(
	                "UPDATE PRODUCTO SET "
	                + " NOMBRE = ?, "
	                + " DESCRIPCION = ?,"
	                + " CANTIDAD = ?"
	                + " WHERE ID = ?");

	        try (statement) {
	            statement.setString(1, producto.getNombre());
	            statement.setString(2, producto.getDescripcion());
	            statement.setInt(3, producto.getCantidad());
	            statement.setInt(4, producto.getId());
	            statement.executeUpdate();

	            int updateCount = statement.getUpdateCount();

	            return updateCount;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	public List<Producto> listar(Integer id) {
		List<Producto> resultado = new ArrayList<>(); 
				
				final Connection con = new ConnectionFactory().recuperaConexion();
			
				
				try(con){
					
					String sqlQuery = "select * from producto where categoria_id = ?";
					final PreparedStatement statement = con.prepareStatement(sqlQuery);
					
					try(statement){
						
						statement.setInt(1, id);
						statement.execute();
						
						ResultSet resultSet = statement.getResultSet();
						
						while(resultSet.next()) {
							Producto fila = new Producto();
							fila.setId(resultSet.getInt(1));
							fila.setNombre(resultSet.getString(2));
							fila.setDescripcion(resultSet.getString(3));
							fila.setCantidad(resultSet.getInt(4));
							
							resultado.add(fila);
						}
					
						return resultado;
					}
					
				}catch (SQLException e) {
					throw new RuntimeException(e);
				}
	}
	
	
	
	
	
	
	
	
}
