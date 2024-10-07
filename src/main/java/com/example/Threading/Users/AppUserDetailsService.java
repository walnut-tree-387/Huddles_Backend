package com.example.Threading.Users;

import com.example.Threading.SecurityConfiguration.JWT.JwtHelper;
import com.example.Threading.Users.Dto.LoginRequestDto;
import com.example.Threading.Users.Dto.LoginResponseDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserService appUserService;
    private final JwtHelper jwtHelper;

    public AppUserDetailsService(AppUserService appUserService, JwtHelper jwtHelper) {
        this.appUserService = appUserService;
        this.jwtHelper = jwtHelper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getByUserName(username);
        return buildUserDetails(user);
    }
    public UserDetails buildUserDetails(AppUser user){
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getUserRole().toString());
        return User.builder().username(user.getUserName()).password(user.getPassword()).authorities(authority).build();
    }
    public LoginResponseDto authenticate(LoginRequestDto dto){
        AppUser user = appUserService.getByUserName(dto.getUserName());
        if(!user.getPassword().equals(dto.getPassword())) throw new RuntimeException("Credentials not matched");
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(jwtHelper.generateToken(buildUserDetails(user), user.getEmail()));
        responseDto.setUserRole(user.getUserRole());
        return responseDto;
    }
    public AppUser getLoggedInUser(){
        User user = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (user == null) return null;
        return appUserService.getByUserName(user.getUsername());
    }
}
