package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id) throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		String sql = "UPDATE PRODUCTO SET NOMBRE = ?, DESCRIPCION = ? WHERE ID = ?";
		
		PreparedStatement pt = con.prepareStatement(sql);
		pt.setString(1, nombre);
		pt.setString(2, descripcion);
		pt.setInt(3, id);
		
		int respuesta = pt.executeUpdate();
		//int respuesta = pt.getUpdateCount();
		pt.close();
		con.close();
		
		return respuesta;
		
	}

	public int eliminar(Integer id) throws SQLException {
		
		int resultado;
		
		//Creacion de la operacion a la base datos
		Connection con = new ConnectionFactory().recuperaConexion();
		
		String sqlQuery = "DELETE FROM PRODUCTO WHERE ID = ?";
		
		PreparedStatement statement = con.prepareStatement(sqlQuery);
		statement.setInt(1, id);
		
		statement.executeUpdate();
		
		resultado = statement.getUpdateCount();
		
		statement.close();
		con.close();
		
		return resultado;
		
		
	}

	public List<Map<String,String>> listar() throws SQLException {
		
		Connection con = new ConnectionFactory().recuperaConexion();
		
		
		Statement statement = con.createStatement();
		
		String sql = "select * from producto";
		
		Boolean result = statement.execute(sql);
		
		ResultSet resultSet = statement.getResultSet();
		
		List<Map<String,String>> resultado = new ArrayList<>();
		
		while(resultSet.next()) {
			Map<String,String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt(1)));
			fila.put("NOMBRE", resultSet.getString(2));
			fila.put("DESCRIPCION", resultSet.getString(3));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt(4)));
			
			resultado.add(fila);
		}
		
		con.close();
		
		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
    	
    	String nombre = producto.get("NOMBRE");
    	String descripcion = producto.get("DESCRIPCION");
    	Integer cantidad = Integer.valueOf(producto.get("CANTIDAD"));
    	Integer maximaCantidad = 50;
    	
		Connection con = new ConnectionFactory().recuperaConexion();
		//Aqui desactivamos el autocommit de la transaccion
		//Se utiliza para ejecutar instrucciones mas complejas
		con.setAutoCommit(false);
		
		
		PreparedStatement statement = con.prepareStatement("insert into producto(nombre, descripcion, cantidad)" 
				+ " values(?,?,?)",Statement.RETURN_GENERATED_KEYS); 
		//Englobamos las instrucciones que son potenciales de una exception para trabajar con transacciones
		try {
			do {
				int cantidadParaGuardar = Math.min(cantidad, maximaCantidad);
				ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
				cantidad -= maximaCantidad;
			} while (cantidad>0);
			
			con.commit();
			System.out.println("La transacción se llevó a cabo.");
			
		} catch (Exception e) {
			con.rollback();
			System.out.println("La transacción no se llevó a cabo: "+ e.getMessage());
		}
		
		statement.close();
		con.close();
    	
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
			throws SQLException {
		
		if(cantidad>=5) {
			throw new RuntimeException();
		}
		
		statement.setString(1, nombre);
		statement.setString(2, descripcion);
		statement.setInt(3, cantidad);
		
		statement.execute();
		
		ResultSet resultset = statement.getGeneratedKeys();
    	
		while(resultset.next()) {
			System.out.println(
					String.format("El producto insertado tiene como ID %d", resultset.getInt(1)));	
			
		}
	}

    public void otraFormaEliminar() {
    	//Connection con = new ConnectionFactory().recuperaConexion();
		
		//String sql = "delete from producto where id = ?";
		
		//PreparedStatement pStatement = con.prepareStatement(sql);
		
		//pStatement.setInt(1, String.valueOf(1)/*id*/);
		
		//int resul = pStatement.executeUpdate();
		
		//if(resul == 1) {
		//	System.out.println("Una fila fue eliminada");
		//}
	
    }
    
}
