package com.finalpos.POSsystem.Model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChangePasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
