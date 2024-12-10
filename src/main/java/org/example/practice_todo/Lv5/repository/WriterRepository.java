package org.example.practice_todo.Lv5.repository;

import java.util.List;
import org.example.practice_todo.Lv5.entity.Writer;

public interface WriterRepository {

  Writer findWriterByIdOrElseThrow(Long writerId);

  List<Writer> findWriterByName(String writerName);
}
