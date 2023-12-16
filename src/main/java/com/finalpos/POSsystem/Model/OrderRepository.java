package com.finalpos.POSsystem.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderModel, String> {
//    List<OrderModel> findByCustomer_id(String customerID);
//    OrderModel findByOrder_number(String orderNumber);
}
