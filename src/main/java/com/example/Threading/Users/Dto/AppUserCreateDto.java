package com.example.Threading.Users.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppUserCreateDto extends AppUserBaseDto{
    private UUID uuid;
    private String password;
}
