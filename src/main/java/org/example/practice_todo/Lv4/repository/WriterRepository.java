package org.example.practice_todo.Lv4.repository;

import java.util.List;
import org.example.practice_todo.Lv4.entity.Writer;

public interface WriterRepository {

  Writer findWriterByIdOrElseThrow(Long writerId);

  List<Writer> findWriterByName(String writerName);
}
