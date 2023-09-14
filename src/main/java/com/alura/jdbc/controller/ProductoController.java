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
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.persistencia.ProductoDAO;

public class ProductoController {
	
	private ProductoDAO productoDAO = new ProductoDAO();
	

	public int modificar(Producto producto) {
		return productoDAO.modificar(producto);
	}

	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
		
	}

	public List<Producto> listar() {
		return productoDAO.listar();
		
	}
	
	public List<Producto> listar(Categoria categoria){
		return productoDAO.listar(categoria.getId());
	}

    public void guardar(Producto producto, Integer categoriaId) {
    	producto.setCategoriaId(categoriaId);
    	productoDAO.guardar(producto);
		
	}

	

   
    
}
