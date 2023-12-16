package com.finalpos.POSsystem.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderModel {
    @Id
    private String id;
    private String order_number;
    private String customer_id;
    private String staff_id;

    private int taxrate;
    private int taxfee;
    private int sub_total;
    private int cash;
    private int change;
    private int total;
    private int quantity;

    private int paymentMethod;

    private String created_date;
}
