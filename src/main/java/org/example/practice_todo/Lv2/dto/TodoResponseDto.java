package org.example.practice_todo.Lv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.practice_todo.Lv2.entity.Todo;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

  // ID
  private Long id;
  // 일정 내용
  private String contents;
  // 작성자명
  private String writerName;
  // 생성일
  private String createDate;
  // 수정일
  private String modifyDate;

  public TodoResponseDto(Todo todo) {
    this.id = todo.getId();
    this.contents = todo.getContents();
    this.writerName = todo.getWriterName();
    this.createDate = todo.getCreateDate();
    this.modifyDate = todo.getModifyDate();
  }
}
