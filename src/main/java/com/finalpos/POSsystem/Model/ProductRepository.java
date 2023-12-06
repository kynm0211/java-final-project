package com.finalpos.POSsystem.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<ProductModel, String> {
    ProductModel findByBarcode(String barcode);
}
