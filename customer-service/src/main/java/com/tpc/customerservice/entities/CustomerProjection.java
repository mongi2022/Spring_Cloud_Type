package com.tpc.customerservice.entities;

import org.springframework.data.rest.core.config.Projection;
@Projection(name = "fullCostumer",types = Customer.class)
public interface CustomerProjection {
      String getName();
}
