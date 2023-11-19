package com.finalpos.POSsystem.Model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<UserModel, String> {
    UserModel findByUsername(String username);
}
