package org.example.practice_todo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.example.practice_todo.dto.TodoRequestDto;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Writer;
import org.example.practice_todo.service.TodoService;
import org.example.practice_todo.service.WriterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {

  // 일정 관리 서비스
  private final TodoService todoService;
  // 작성자 서비스
  private final WriterService writerService;

  public TodoController(TodoService todoService, WriterService writerService) {
    this.todoService = todoService;
    this.writerService = writerService;
  }

  /**
   * 일정 생성 API 요청
   * <p>
   * 받은 데이터로 일정 생성 및 등록
   *
   * @param dto 등록에 사용할 요청 데이터
   * @return 등록된 일정 정보
   */
  @PostMapping
  public ResponseEntity<TodoResponseDto> createTodo(@RequestBody TodoRequestDto dto) {
    Writer writer = this.writerService.findWriterById(dto.getWriterId());

    TodoResponseDto todoResponseDto = this.todoService.createTodo(dto);
    todoResponseDto.setWriterName(writer.getName());

    return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
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
      @RequestParam(required = false) String modifyDate,
      @RequestParam(required = false) int pageNumber,
      @RequestParam(required = false) int pageSize) {
    List<Writer> writerList = new ArrayList<>();
    if (!Objects.isNull(writerName)) {
      writerList = this.writerService.findWriterByName(writerName);

      // 작성자 정보 테이블에 일치하는 작성자명이 없는 경우
      if (writerList.isEmpty()) {
        return new ResponseEntity<>(null, HttpStatus.OK);
      }
    }

    List<TodoResponseDto> todoResponseDtoList = this.todoService.findAllTodo(writerList,
        modifyDate, pageNumber, pageSize);
    todoResponseDtoList = this.writerService.setWriterName(todoResponseDtoList);

    return new ResponseEntity<>(todoResponseDtoList, HttpStatus.OK);
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
    TodoResponseDto todoResponseDto = this.todoService.findTodoById(id);

    Writer writer = this.writerService.findWriterById(todoResponseDto.getWriterId());
    todoResponseDto.setWriterName(writer.getName());

    return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
  }

  /**
   * 선택 일정 수정 API
   * <p>
   * 일정 ID와 패스워드로 조회하며 일정 내용, 작성자명만 수정 가능
   *
   * @param id  일정 ID
   * @param dto 수정에 사용할 요청 데이터
   * @return 수정된 일정 정보
   */
  @PatchMapping("/{id}")
  public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id,
      @RequestBody TodoRequestDto dto) {
    TodoResponseDto todoResponseDto = this.todoService.updateTodo(id, dto);

    Writer writer = this.writerService.findWriterById(todoResponseDto.getWriterId());
    todoResponseDto.setWriterName(writer.getName());

    return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
  }

  /**
   * 선택 일정 삭제 API
   * <p>
   * 일정 ID와 패스워드로 조회하며 해당 일정 삭제
   *
   * @param id  일정 ID
   * @param dto 삭제에 사용할 요청 데이터
   * @return 삭제 결과
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTodo(@PathVariable Long id, @RequestBody TodoRequestDto dto) {
    this.todoService.deleteTodo(id, dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
