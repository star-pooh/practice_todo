package org.example.practice_todo.Lv6.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.practice_todo.Lv6.entity.Todo;

@Getter
@Setter
public class TodoResponseDto {

  // ID
  private Long id;
  // 일정 내용
  private String contents;
  // 작성자 ID
  private Long writerId;
  // 작성자명
  private String writerName;
  // 생성일
  private String createDate;
  // 수정일
  private String modifyDate;

  public TodoResponseDto(Todo todo) {
    this.id = todo.getId();
    this.contents = todo.getContents();
    this.writerId = todo.getWriterId();
    this.createDate = todo.getCreateDate();
    this.modifyDate = todo.getModifyDate();
  }

  public TodoResponseDto(Long id, String contents, Long writerId, String createDate,
      String modifyDate) {
    this.id = id;
    this.contents = contents;
    this.writerId = writerId;
    this.createDate = createDate;
    this.modifyDate = modifyDate;
  }
}
