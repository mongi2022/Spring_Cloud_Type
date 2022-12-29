package com.tpc.billingservice.repositories;

import com.tpc.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {
}
