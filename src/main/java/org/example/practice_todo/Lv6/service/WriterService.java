package org.example.practice_todo.Lv6.service;

import java.util.List;
import org.example.practice_todo.Lv6.dto.TodoResponseDto;
import org.example.practice_todo.Lv6.entity.Writer;

public interface WriterService {

  Writer findWriterById(Long writerId);

  List<Writer> findWriterByName(String writerName);

  List<TodoResponseDto> setWriterName(List<TodoResponseDto> dtoList);
}