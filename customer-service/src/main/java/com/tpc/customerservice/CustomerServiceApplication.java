package com.tpc.customerservice;

import com.tpc.customerservice.entities.Customer;
import com.tpc.customerservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	@Bean
	CommandLineRunner start(CustomerRepository consumerRepository, RepositoryRestConfiguration restConfiguration){
		restConfiguration.exposeIdsFor(Customer.class);
		return args -> {
			List costumers=List.of(
					Customer.builder().name("mongi").email("mongi@gmail.com").build(),
					Customer.builder().name("adem").email("adem@gmail.com").build()
			);
			consumerRepository.saveAll(	costumers);
			consumerRepository.findAll().forEach(System.out::println);

		};
	}

}
