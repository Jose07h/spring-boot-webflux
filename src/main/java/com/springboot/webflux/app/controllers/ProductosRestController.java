package com.springboot.webflux.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.webflux.app.models.dao.ProductoDao;
import com.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/productos")
public class ProductosRestController {
	@Autowired
	public ProductoDao productoDao;

	public static final Logger log=LoggerFactory.getLogger(ProductosRestController.class);
	@GetMapping("")
	public Flux<Producto> index() {
		
		Flux<Producto> productos = productoDao.findAll().map(producto -> {
			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});
		return productos;
	}
	
	@GetMapping("/{id}")
	public Mono<Producto> buscar(@PathVariable String id) {
		Mono<Producto> producto = productoDao.findById(id);
//		Flux<Producto> productos = productoDao.findAll();
//		Mono<Producto> producto=productos.filter(p->p.getId().equals(id)).next();
		return producto;
	}
}
