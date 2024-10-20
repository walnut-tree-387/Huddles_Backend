package com.example.Threading.exception.types;

public class HuddleNotFoundException extends RuntimeException {
    public HuddleNotFoundException(Class<?> clazz, String message) {
        super(String.format(message, clazz.getSimpleName()));
    }
}
