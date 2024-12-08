package org.example.practice_todo.controller;

import java.util.List;
import org.example.practice_todo.dto.TodoRequestDto;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  @PostMapping
  public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
    return new ResponseEntity<>(this.todoService.createTodo(dto), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<TodoResponseDto>> findAllTodo(
      @RequestParam(required = false) String writerName,
      @RequestParam(required = false) String modifyDate) {
    return new ResponseEntity<>(
        this.todoService.findAllTodo(writerName, modifyDate), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
    return new ResponseEntity<>(this.todoService.findTodoById(id), HttpStatus.OK);
  }
}
