package com.tpc.billingservice.web;

import com.tpc.billingservice.entities.Bill;
import com.tpc.billingservice.repositories.BillRepository;
import com.tpc.billingservice.repositories.ProductItemRepository;
import com.tpc.billingservice.service.CustomerRestClient;
import com.tpc.billingservice.service.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BillRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
  private CustomerRestClient customerRestClient;
  private ProductRestClient productRestClient;
    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }
    @GetMapping("/bills/{id}")
    public Bill getBillById(@PathVariable Long id){
        Bill bill= billRepository.findById(id).orElseThrow(null);
        bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
        bill.getProductItems().forEach(p->{
            p.setProduct(productRestClient.findProductById(p.getProductId()));
        });
        return bill;
    }
}
