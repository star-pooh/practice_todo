package org.example.practice_todo.Lv3.repository;

import java.util.List;
import org.example.practice_todo.Lv3.dto.TodoResponseDto;
import org.example.practice_todo.Lv3.entity.Todo;

public interface TodoRepository {

  TodoResponseDto createTodo(Todo todo);

  List<TodoResponseDto> findAllTodo();

  List<TodoResponseDto> findAllTodoWithWriterName(Long writerId);

  List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate);

  List<TodoResponseDto> findAllTodoWithWriterNameAndModifyDate(
      Long writerId, String modifyDate);

  Todo findTodoByIdOrElseThrow(Long id);

  int updateTodo(Long id, String contents, String password);

  int deleteTodo(Long id, String password);
}
