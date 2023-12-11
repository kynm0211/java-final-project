package com.finalpos.POSsystem.Model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailModel {
    @Id
    private String id;

    private String order_id;
    private String order_number;

    private ArrayList<ProductCartModel> products;

}
