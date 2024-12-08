package org.example.practice_todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.practice_todo.entity.Todo;

@Getter
@AllArgsConstructor
public class TodoResponseDto {
  private Long id;
  private String contents;
  private String writerName;
  private String createDate;
  private String modifyDate;

  public TodoResponseDto(Todo todo) {
    this.id = todo.getId();
    this.contents = todo.getContents();
    this.writerName = todo.getWriterName();
    this.createDate = todo.getCreateDate();
    this.modifyDate = todo.getModifyDate();
  }
}
