package com.example.Threading.exception.types;

public class HuddleMappingException extends RuntimeException {
    public HuddleMappingException (Class<?> clazz, String message) {
        super(String.format(message, clazz.getSimpleName()));
    }
}
