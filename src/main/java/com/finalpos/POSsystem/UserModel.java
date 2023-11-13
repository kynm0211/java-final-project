package com.finalpos.POSsystem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserModel {
    @Id
    @GeneratedValue(generator = "uuid")
    private String id;
    private String name;
    private String email;
    private String password;
    private String role;
    private String status;
    private String created_at;
    private String username;
}
