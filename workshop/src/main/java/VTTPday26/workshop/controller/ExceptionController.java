package VTTPday26.workshop.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import VTTPday26.workshop.model.ApiError;
import VTTPday26.workshop.model.IDNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ExceptionController {
    
    @ExceptionHandler(IDNotFoundException.class)
    public ResponseEntity<ApiError> handleIdNotFoundException(IDNotFoundException ex, HttpServletRequest request, HttpServletResponse response){
        ApiError apiError = new ApiError(404, ex.getMessage(), new Date());
        
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }


}
