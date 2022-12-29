package com.tpc.billingservice.repositories;

import com.tpc.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

public interface BillRepository extends JpaRepository<Bill,Long> {
}
