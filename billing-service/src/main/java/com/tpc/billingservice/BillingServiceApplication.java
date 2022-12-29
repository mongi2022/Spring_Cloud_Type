package com.tpc.billingservice;

import com.tpc.billingservice.entities.Bill;
import com.tpc.billingservice.entities.ProductItem;
import com.tpc.billingservice.model.Customer;
import com.tpc.billingservice.model.Product;
import com.tpc.billingservice.repositories.BillRepository;
import com.tpc.billingservice.repositories.ProductItemRepository;
import com.tpc.billingservice.service.CustomerRestClient;
import com.tpc.billingservice.service.ProductRestClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
@Bean
	CommandLineRunner start(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient , ProductRestClient productRestClient){
		return args -> {
			Collection<Product> products=productRestClient.allProducts().getContent();
			System.out.println(products);
			Long customerId=1L;
			Customer customer=customerRestClient.findCustomerById(customerId);
		if(customer==null) throw new RuntimeException("customer not found");
			Bill bill=new Bill();
			bill.setBillDate(new Date());
			bill.setCustomerId(customerId);
			Bill savedBill=billRepository.save(bill);
			products.forEach(p->{
				ProductItem productItem=new ProductItem();
				productItem.setBill(savedBill);
				productItem.setProductId(p.getId());
				productItem.setQuantity(1+new Random().nextInt(10));
				productItem.setPrice(p.getPrice());
				productItem.setDiscount(Math.random());
				productItemRepository.save(productItem);
			});
		};
}
}
