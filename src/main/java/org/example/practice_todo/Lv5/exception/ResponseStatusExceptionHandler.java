package org.example.practice_todo.Lv5.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ResponseStatusExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(
      ResponseStatusException e) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", e.getReason());
    response.put("status", e.getStatusCode().value());

    return ResponseEntity.status(e.getStatusCode()).body(response);
  }
}
