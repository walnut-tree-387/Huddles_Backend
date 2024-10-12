package com.example.Threading.SecurityConfiguration.JWT;

import com.example.Threading.Users.AppUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final AppUserDetailsService userDetailsService;


    public JwtTokenAuthenticationFilter(JwtHelper jwtHelper, AppUserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Jwt Filter starting processing for route ->" + request.getRequestURI());
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        try{
            if(requestHeader!= null && requestHeader.startsWith("Bearer")){
                token = requestHeader.substring(7);
                username = jwtHelper.getUsernameFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtHelper.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Successful Token Validation. User access granted");
                }
                else{
                    log.info("Token verification failed");
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Jwt Filter failed to process request", e);
        }
        filterChain.doFilter(request, response);
    }

}