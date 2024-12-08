package org.example.practice_todo.service;

import java.util.List;
import org.example.practice_todo.dto.TodoRequestDto;
import org.example.practice_todo.dto.TodoResponseDto;

public interface TodoService {
  TodoResponseDto createTodo(TodoRequestDto dto);

  List<TodoResponseDto> findAllTodo(String writerName, String modifyDate);

  TodoResponseDto findTodoById(Long id);
}
