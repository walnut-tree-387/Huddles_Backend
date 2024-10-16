package com.example.Threading.WebSocket;

import lombok.Setter;

import javax.security.auth.Subject;
import java.security.Principal;

@Setter
public class StompPrincipal implements Principal {
    private String name;
    public StompPrincipal(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
