package org.datn.app.core.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String username;
    private String email;
    private String code;
    private String password;
}

