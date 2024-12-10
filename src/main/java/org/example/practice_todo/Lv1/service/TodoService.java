package org.example.practice_todo.Lv1.service;

import java.util.List;
import org.example.practice_todo.Lv1.dto.TodoRequestDto;
import org.example.practice_todo.Lv1.dto.TodoResponseDto;

public interface TodoService {

  TodoResponseDto createTodo(TodoRequestDto dto);

  List<TodoResponseDto> findAllTodo(String writerName, String modifyDate);

  TodoResponseDto findTodoById(Long id);
}
