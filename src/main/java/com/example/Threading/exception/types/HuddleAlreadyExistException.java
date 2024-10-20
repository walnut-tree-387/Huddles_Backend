package com.example.Threading.exception.types;

public class HuddleAlreadyExistException extends RuntimeException {
    public HuddleAlreadyExistException(Class<?> clazz, String message) {
        super(String.format(message, clazz.getSimpleName()));
    }
}
