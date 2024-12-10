package org.example.practice_todo.Lv4.repository;

import java.util.List;
import org.example.practice_todo.Lv4.dto.TodoResponseDto;
import org.example.practice_todo.Lv4.entity.Todo;
import org.example.practice_todo.Lv4.util.Paging;

public interface TodoRepository {

  TodoResponseDto createTodo(Todo todo);

  List<TodoResponseDto> findAllTodo(Paging paging);

  List<TodoResponseDto> findAllTodoWithWriterId(List<Long> writerIdList, Paging paging);

  List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate, Paging paging);

  List<TodoResponseDto> findAllTodoWithWriterIdAndModifyDate(
      List<Long> writerIdList, String modifyDate, Paging paging);

  Todo findTodoByIdOrElseThrow(Long id);

  int updateTodo(Long id, String contents, String password);

  int deleteTodo(Long id, String password);

  long countTotalTodo();

  long countTotalTodoWithWriterId(Long writerId);

  long countTotalTodoWithModifyDate(String modifyDate);

  long countTotalTodoWithWriterIdAndModifyDate(Long writerId, String modifyDate);
}
