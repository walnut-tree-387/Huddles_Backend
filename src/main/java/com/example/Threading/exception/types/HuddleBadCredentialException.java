package com.example.Threading.exception.types;

public class HuddleBadCredentialException extends RuntimeException{
   public HuddleBadCredentialException (Class<?> clazz, String message) {
       super(String.format(message, clazz.getSimpleName()));
   }
}
