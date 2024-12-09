package org.example.practice_todo.service;

import java.util.List;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Writer;

public interface WriterService {

  Writer findWriterById(Long writerId);

  List<Writer> findWriterByName(String writerName);

  List<TodoResponseDto> setWriterName(List<TodoResponseDto> dtoList);
}
