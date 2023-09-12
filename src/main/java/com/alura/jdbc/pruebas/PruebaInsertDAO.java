package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.persistencia.ProductoDAO;

public class PruebaInsertDAO {

	public static void main(String[] args) {
		
		Producto miProducto = new Producto();
		miProducto.setNombre("Papayas");
		miProducto.setDescripcion("Frutas");
		miProducto.setCantidad(13);
		
		ProductoDAO proDao = new ProductoDAO();
		proDao.guardar(miProducto);
		
		
		
		
		
	}

}
