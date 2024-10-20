package com.example.Threading.exception;
import com.example.Threading.exception.types.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static final String INVALID_PATH = "Invalid Path";

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException me) {
        Map<String,Object> response = new HashMap<>();
        for (FieldError fe : me.getFieldErrors()) {
            response.put(fe.getField(), fe.getDefaultMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationExceptions(ValidationException ve) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", ve.getMessage());
        response.put("code", HttpStatus.BAD_REQUEST);
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HuddleException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleHuddleException(HuddleException re) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("timestamp", LocalDateTime.now());
        logger.info(re.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({HuddleBadCredentialException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHuddleBadCredentialExceptions(HuddleBadCredentialException re) {
        Map<String,Object> response = new HashMap<>();
        response.put("message : ", re.getMessage());
        response.put("code", HttpStatus.BAD_REQUEST);
        response.put("timestamp", LocalDateTime.now());
        logger.info(re.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HuddleMappingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleHuddleMappingExceptions(HuddleMappingException re) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR);
        response.put("timestamp", LocalDateTime.now());
        logger.info(re.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({HuddleNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(HuddleNotFoundException re) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", re.getMessage());
        response.put("code", HttpStatus.NOT_FOUND);
        response.put("timestamp", LocalDateTime.now());
        logger.info(re.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({HuddleAlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(HuddleAlreadyExistException re) {
        Map<String,Object> response = new HashMap<>();
        response.put("message", re.getMessage());
        response.put("code", 409);
        response.put("timestamp", LocalDateTime.now());
        logger.info(re.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

//    @ExceptionHandler({HuddleFileNotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<Object> handleHuddleFileNotFoundException(HuddleFileNotFoundException re) {
//        Map<String,Object> response = new HashMap<>();
//        response.put("message", re.getMessage());
//        response.put("code", HttpStatus.NOT_FOUND);
//        response.put("timestamp", LocalDateTime.now());
//        logger.info(re.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler({MinIoException.class})
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public ResponseEntity<Object> handleMinIOException(MinIoException re) {
//        Map<String,Object> response = new HashMap<>();
//        response.put("message", re.getMessage());
//        response.put("code", HttpStatus.BAD_GATEWAY);
//        response.put("timestamp", LocalDateTime.now());
//        logger.info(re.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//    @ExceptionHandler({HuddleAlreadyExistException.class})
//    @ResponseStatus(HttpStatus.CONFLICT)
//    public ResponseEntity<Object> handleResourceAlreadyExistsException(HuddleAlreadyExistException re) {
//        Map<String,Object> response = new HashMap<>();
//        response.put("message", re.getMessage());
//        response.put("code", 409);
//        response.put("timestamp", LocalDateTime.now());
//        response.put("duplicate", re.getDto());
//        logger.info(re.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//    }

    //    @ExceptionHandler({ParserServiceUnavailableException.class})
//    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
//    public ResponseEntity<Object> handleParserServiceUnavailableExceptions(
//            ParserServiceUnavailableException ve) {
//        Map<String,Object> response = new HashMap<>();
//        response.put("message", PARSER_SERVICE_UNAVAILABLE);
//        response.put("code", 503);
//        response.put("timestamp", LocalDateTime.now());
//        logger.info(ve.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
//    }
//    @ExceptionHandler({ParserServiceFailureException.class})
//    @ResponseStatus(HttpStatus.BAD_GATEWAY)
//    public ResponseEntity<Object> handleParserServiceFailureException(
//            ParserServiceFailureException e) {
//        Map<String,Object> response = new HashMap<>();
//        response.put("message", PARSER_FAILURE);
//        response.put("code", 502);
//        response.put("timestamp", LocalDateTime.now());
//        logger.info(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
//    }

}