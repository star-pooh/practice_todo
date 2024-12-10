package org.example.practice_todo.Lv2.repository;

import java.util.List;
import org.example.practice_todo.Lv2.dto.TodoResponseDto;
import org.example.practice_todo.Lv2.entity.Todo;

public interface TodoRepository {

  TodoResponseDto createTodo(Todo todo);

  List<TodoResponseDto> findAllTodo();

  List<TodoResponseDto> findAllTodoWithWriterName(String writerName);

  List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate);

  List<TodoResponseDto> findAllTodoWithWriterNameAndModifyDate(
      String writerName, String modifyDate);

  Todo findTodoByIdOrElseThrow(Long id);

  int updateTodo(Long id, String contents, String writerName, String password);

  int deleteTodo(Long id, String password);
}
