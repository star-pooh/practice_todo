package org.example.practice_todo.service;

import java.util.List;
import java.util.Objects;
import org.example.practice_todo.dto.TodoRequestDto;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Todo;
import org.example.practice_todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

  // 일정 관리 앱 레포지토리
  private final TodoRepository todoRepository;

  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  /**
   * 일정 생성 및 등록
   *
   * @param dto 요청 데이터
   * @return 등록된 일정 정보
   */
  @Override
  public TodoResponseDto createTodo(TodoRequestDto dto) {
    Todo todo = new Todo(dto.getContents(), dto.getWriterName(), dto.getPassword());
    return this.todoRepository.createTodo(todo);
  }

  /**
   * 전체 일정 조회
   * <p>
   * 작성자명, 수정일의 입력에 따라 조회 결과가 다름
   *
   * @param writerName 작성자명
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodo(String writerName, String modifyDate) {
    if (!Objects.isNull(writerName) && Objects.isNull(modifyDate)) {
      return this.todoRepository.findAllTodoWithWriterName(writerName);
    } else if (Objects.isNull(writerName) && !Objects.isNull(modifyDate)) {
      return this.todoRepository.findAllTodoWithModifyDate(modifyDate);
    } else if (!Objects.isNull(writerName) && !Objects.isNull(modifyDate)) {
      return this.todoRepository.findAllTodoWithWriterNameAndModifyDate(writerName, modifyDate);
    } else {
      return this.todoRepository.findAllTodo();
    }
  }

  /**
   * 선택 일정 조회
   * <p>
   * 일정 ID로 조회
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  @Override
  public TodoResponseDto findTodoById(Long id) {
    Todo todo = this.todoRepository.findTodoByIdOrElseThrow(id);
    return new TodoResponseDto(todo);
  }
}
