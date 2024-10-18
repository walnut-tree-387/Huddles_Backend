package com.example.Threading.WebSocket;

import com.example.Threading.SecurityConfiguration.JWT.JwtHelper;
import com.example.Threading.Users.AppUser;
import com.example.Threading.Users.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;
@Slf4j
@Component
public class TokenInterceptor extends HttpSessionHandshakeInterceptor {
    private final JwtHelper jwtHelper;
    private final AppUserService appUserService;

    public TokenInterceptor(JwtHelper jwtHelper, AppUserService appUserService) {
        this.jwtHelper = jwtHelper;
        this.appUserService = appUserService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");
            if (token != null) {
                String username = jwtHelper.getUsernameFromToken(token);
                AppUser user = appUserService.getByUserName(username);
                attributes.put("userUuid", user.getUuid());
            } else {
                return false;
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}
