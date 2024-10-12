package com.example.Threading.Users;

import com.example.Threading.Users.Dto.AppUserCreateDto;
import com.example.Threading.Users.Dto.LoginRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/users")
@RestController
public class AppUserController {
    private final AppUserService appUserService;
    private final AppUserDetailsService userDetailsService;

    public AppUserController(AppUserService appUserService, AppUserDetailsService userDetailsService) {
        this.appUserService = appUserService;
        this.userDetailsService = userDetailsService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody AppUserCreateDto appUser) {
        appUserService.create(appUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDto dto){
        return new ResponseEntity<>(userDetailsService.authenticate(dto), HttpStatus.OK);
    }
    @GetMapping("/filter-by-huddle/{huddleUuid}")
    public ResponseEntity<?> getUsers(@PathVariable("huddleUuid")UUID huddleUuid){
        return new ResponseEntity<>(appUserService.getUsersNotInAHuddle(huddleUuid), HttpStatus.OK);
    }
}
