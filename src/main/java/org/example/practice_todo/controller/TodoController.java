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

  // 일정 관리 앱 서비스
  private final TodoService todoService;

  public TodoController(TodoService todoService) {
    this.todoService = todoService;
  }

  /**
   * 일정 생성 API 요청
   * <p>
   * 받은 데이터로 일정 생성 및 등록
   *
   * @param dto 요청 데이터
   * @return 등록된 일정 정보
   */
  @PostMapping
  public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
    return new ResponseEntity<>(this.todoService.createTodo(dto), HttpStatus.CREATED);
  }

  /**
   * 전체 일정 조회 API
   * <p>
   * 작성자명, 수정일을 조건으로 조회
   *
   * @param writerName 작성자명
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  @GetMapping
  public ResponseEntity<List<TodoResponseDto>> findAllTodo(
      @RequestParam(required = false) String writerName,
      @RequestParam(required = false) String modifyDate) {
    return new ResponseEntity<>(
        this.todoService.findAllTodo(writerName, modifyDate), HttpStatus.OK);
  }

  /**
   * 선택 일정 조회 API
   * <p>
   * 일정 ID로 조회
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  @GetMapping("/{id}")
  public ResponseEntity<TodoResponseDto> findTodoById(@PathVariable Long id) {
    return new ResponseEntity<>(this.todoService.findTodoById(id), HttpStatus.OK);
  }
}
