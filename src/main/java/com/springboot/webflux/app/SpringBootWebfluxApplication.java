package com.springboot.webflux.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.springboot.webflux.app.models.dao.ProductoDao;
import com.springboot.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	public ProductoDao productoDao;

	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("productos").subscribe();

		Flux.just(new Producto("Leche", 31.60), new Producto("Arroz", 6.69), new Producto("Maizena", 1.99),
				new Producto("Café soluble", 21.99), new Producto("Frijol", 14.00), new Producto("Sopa", 1.69),
				new Producto("Huevos", 10.40), new Producto("Consomate", 4.99)
		).flatMap(producto -> {
			producto.setFechaCreacion(new Date());
			return productoDao.save(producto);
		}).subscribe();
	}

}
