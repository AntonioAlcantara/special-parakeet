package com.parakeet.lol.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@Log4j2
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = StudentExistsException.class)
    public ResponseEntity<String> handleStudentExistsException(StudentExistsException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = StudentNotExistsException.class)
    public ResponseEntity<String> handleStudentNotExistsException(StudentNotExistsException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = StudentIsAlreadySubscribedException.class)
    public ResponseEntity<String> handleStudentIsAlreadySubscribedException(StudentIsAlreadySubscribedException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UniversityNotExistsException.class)
    public ResponseEntity<String> handleUniversityNotExistsException(UniversityNotExistsException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<String> handleException(ValidationException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        var errorMessage = String.format(
                "The parameter %s %s",
                ex.getBindingResult().getFieldErrors().get(0).getField(),
                ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()
        );
        return ResponseEntity.status(status)
                .body(
                        errorMessage != null && errorMessage.isEmpty() ?
                                "We have internal errors, being a Java developer is not stressful at all" :
                                errorMessage
                );
    }
}
