package com.finalpos.POSsystem.Model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {
    private String token;
    private UserModel userModel;
}
