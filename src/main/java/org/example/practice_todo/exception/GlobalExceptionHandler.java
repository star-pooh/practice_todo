package org.example.practice_todo.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * ResponseStatusException 핸들러
   *
   * @param e ResponseStatusException
   * @return HTTP 상태 코드 및 에러 메시지
   */
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatusException(
      ResponseStatusException e) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", e.getReason());
    response.put("status", e.getStatusCode().value());

    return ResponseEntity.status(e.getStatusCode()).body(response);
  }

  /**
   * MethodArgumentNotValidException 핸들러
   *
   * @param e MethodArgumentNotValidException
   * @return HTTP 상태 코드 및 에러 메시지
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", e.getBody().getDetail());
    response.put("detail", e.getDetailMessageArguments()[1]);
    response.put("status", e.getStatusCode().value());

    return ResponseEntity.status(e.getStatusCode()).body(response);
  }
}
