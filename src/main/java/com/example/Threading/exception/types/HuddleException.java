package com.example.Threading.exception.types;

public class HuddleException extends RuntimeException{
    public HuddleException(Class<?> clazz, String message) {
        super(String.format(message, clazz.getSimpleName()));
    }
}
