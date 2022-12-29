package com.tpc.inventaryservice;

import com.tpc.inventaryservice.entities.Product;
import com.tpc.inventaryservice.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventaryServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ProductRepository consumerRepository, RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Product.class);
		return args -> {
			List products=List.of(
					Product.builder().name("ordinateur").price(5000).quantity(5).build(),
					Product.builder().name("imprimante").price(8000).quantity(10).build()
			);
			consumerRepository.saveAll(	products);
			consumerRepository.findAll().forEach(System.out::println);

		};
	}
}
