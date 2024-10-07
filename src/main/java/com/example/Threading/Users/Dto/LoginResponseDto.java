package com.example.Threading.Users.Dto;

import com.example.Threading.Users.UserRole;
import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private UserRole userRole;
}
