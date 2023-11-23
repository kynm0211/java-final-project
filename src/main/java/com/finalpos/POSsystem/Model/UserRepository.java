package com.finalpos.POSsystem.Model;
import com.finalpos.POSsystem.Model.UserModel;
import com.mongodb.client.result.UpdateResult;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

    UserModel findByUsername(String username);


}
