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

  private final TodoRepository todoRepository;

  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  @Override
  public TodoResponseDto createTodo(TodoRequestDto dto) {
    Todo todo = new Todo(dto.getContents(), dto.getWriterName(), dto.getPassword());
    return this.todoRepository.createTodo(todo);
  }

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

  @Override
  public TodoResponseDto findTodoById(Long id) {
    Todo todo = this.todoRepository.findTodoByIdOrElseThrow(id);
    return new TodoResponseDto(todo);
  }
}
