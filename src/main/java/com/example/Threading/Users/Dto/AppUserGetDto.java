package com.example.Threading.Users.Dto;

import com.example.Threading.Users.AppUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppUserGetDto extends AppUserBaseDto {
    private UUID uuid;
}
