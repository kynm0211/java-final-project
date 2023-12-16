package com.finalpos.POSsystem.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerModel, String>{
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
    CustomerModel findByPhone(String phone);
}
