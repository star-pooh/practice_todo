package org.example.practice_todo.Lv2.service;

import java.util.List;
import org.example.practice_todo.Lv2.dto.TodoRequestDto;
import org.example.practice_todo.Lv2.dto.TodoResponseDto;

public interface TodoService {

  TodoResponseDto createTodo(TodoRequestDto dto);

  List<TodoResponseDto> findAllTodo(String writerName, String modifyDate);

  TodoResponseDto findTodoById(Long id);

  TodoResponseDto updateTodo(Long id, TodoRequestDto dto);

  void deleteTodo(Long id, TodoRequestDto dto);
}
