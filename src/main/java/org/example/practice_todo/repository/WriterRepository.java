package org.example.practice_todo.repository;

import java.util.List;
import org.example.practice_todo.entity.Writer;

public interface WriterRepository {

  Writer findWriterByIdOrElseThrow(Long writerId);

  List<Writer> findWriterByName(String writerName);
}
