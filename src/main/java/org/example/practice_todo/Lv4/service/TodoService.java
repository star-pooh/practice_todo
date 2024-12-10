package org.example.practice_todo.Lv4.service;

import java.util.List;
import org.example.practice_todo.Lv4.dto.TodoRequestDto;
import org.example.practice_todo.Lv4.dto.TodoResponseDto;
import org.example.practice_todo.Lv4.entity.Writer;

public interface TodoService {

  TodoResponseDto createTodo(TodoRequestDto dto);

  List<TodoResponseDto> findAllTodo(List<Writer> writerList, String modifyDate, int pageNumber,
      int pageSize);

  TodoResponseDto findTodoById(Long id);

  TodoResponseDto updateTodo(Long id, TodoRequestDto dto);

  void deleteTodo(Long id, TodoRequestDto dto);
}
