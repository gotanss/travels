package com.ingenia.travel.utils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ingenia.travel.domain.exception.PathDuplicateException;
import com.ingenia.travel.domain.exception.PathNotFoundException;
import com.ingenia.travel.domain.exception.StationDuplicateException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class GlobalControllerAdvice {

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(PathNotFoundException.class)
   public ResponseEntity<ErrorResponse> handlePathNotFound(PathNotFoundException e, HttpServletRequest request, HttpServletResponse response) {
      ErrorResponse errorResponse = ErrorResponse
            .builder()
            .path(request.getRequestURI())
            .status(HttpStatus.NOT_FOUND.value())
            .message(ErrorCatalog.PATH_NOT_FOUND.getMessage())
            .timestamp(LocalDateTime.now())
            .build();

      return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler(StationDuplicateException.class)
   public ResponseEntity<ErrorResponse> handleStationDuplicate(StationDuplicateException e, HttpServletRequest request, HttpServletResponse response) {
      ErrorResponse errorResponse = ErrorResponse
            .builder()
            .path(request.getRequestURI())
            .status(HttpStatus.CONFLICT.value())
            .message(ErrorCatalog.STATION_DUPLICATE.getMessage())
            .timestamp(LocalDateTime.now())
            .build();

      return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
   }

   @ResponseStatus(HttpStatus.CONFLICT)
   @ExceptionHandler(PathDuplicateException.class)
   public ResponseEntity<ErrorResponse> handlePathDuplicate(PathDuplicateException e, HttpServletRequest request, HttpServletResponse response) {
      ErrorResponse errorResponse = ErrorResponse
            .builder()
            .path(request.getRequestURI())
            .status(HttpStatus.CONFLICT.value())
            .message(ErrorCatalog.PATH_DUPLICATE.getMessage())
            .timestamp(LocalDateTime.now())
            .build();

      return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
   }

   @ResponseStatus(HttpStatus.BAD_REQUEST)
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
      BindingResult result = ex.getBindingResult();
      return ErrorResponse
            .builder()
            .code(ErrorCatalog.INVALID_PATH.getCode())
            .message(ErrorCatalog.INVALID_PATH.getMessage())
            .details(result.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()))
            .timestamp(LocalDateTime.now())
            .build();
   }

   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   @ExceptionHandler(Exception.class)
   public ErrorResponse handleGenericError(Exception ex) {
      return ErrorResponse
            .builder()
            .code(ErrorCatalog.GENERIC_ERROR.getCode())
            .message(ErrorCatalog.GENERIC_ERROR.getMessage())
            .details(Collections.singletonList(ex.getMessage()))
            .timestamp(LocalDateTime.now())
            .build();
   }

}
