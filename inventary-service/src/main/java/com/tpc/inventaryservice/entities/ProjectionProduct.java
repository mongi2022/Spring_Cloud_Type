package com.tpc.inventaryservice.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productname",types = Product.class)
public interface ProjectionProduct {
    String getName();

}
