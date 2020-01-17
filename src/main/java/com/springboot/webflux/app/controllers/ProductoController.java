package com.springboot.webflux.app.controllers;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;

import com.springboot.webflux.app.models.dao.ProductoDao;
import com.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

	@Autowired
	public ProductoDao productoDao;

	public int cont=0;
	@GetMapping({ "produtos/listar", "/" })
	public String listar(Model model) {
		Flux<Producto> productos = productoDao.findAll().map(producto -> {

			producto.setNombre(producto.getNombre().toUpperCase());
			return producto;
		});

		model.addAttribute("productos", productos);
		model.addAttribute("titulo", "Listado de productos");
		return "productos/productos";
	}

	@GetMapping("produtos/listardatadriver")
	public String listarDatadriver(Model model) {
		Flux<Producto> productos = productoDao.findAll().map(producto -> {
			producto.setNombre(++cont+producto.getNombre().toUpperCase());
			return producto;
		}).delayElements(Duration.ofSeconds(1));

		model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 10));
		model.addAttribute("titulo", "Listado de productos");
		return "productos/productos";
	}

	@GetMapping("produtos/listarfull")
	public String listarFull(Model model) {
		Flux<Producto> productos = productoDao.findAll().map(producto -> {
			producto.setNombre(++cont+"-"+producto.getNombre().toUpperCase());
			return producto;
		}).repeat(5000);

		model.addAttribute("productos",productos);
		model.addAttribute("titulo", "Listado de productos");
		return "productos/productos";
	}
	
	@GetMapping("produtos/listarchunk")
	public String listarChunk(Model model) {
		Flux<Producto> productos = productoDao.findAll().map(producto -> {
			producto.setNombre(++cont+"-"+producto.getNombre().toUpperCase());
			return producto;
		}).repeat(5000);

		model.addAttribute("productos",productos);
		model.addAttribute("titulo", "Listado de productos");
		return "productos/productos-chunked";
	}
}
