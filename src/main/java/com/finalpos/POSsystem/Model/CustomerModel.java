package com.finalpos.POSsystem.Model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerModel {
    @Id
    private String id;

    private String name;
    private String phone;
    private String address;

    @Value("${default.application.avatar}")
    private String defaultAvatar;
    private String image = defaultAvatar;
}
