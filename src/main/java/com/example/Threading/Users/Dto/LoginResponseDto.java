package com.example.Threading.Users.Dto;

import com.example.Threading.Users.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginResponseDto {
    private String token;
    private UserRole userRole;
    private UUID uuid;
    private String name;
}
