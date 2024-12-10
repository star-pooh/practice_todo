package org.example.practice_todo.Lv4.entity;

import lombok.Getter;

@Getter
public class Todo {

  // ID
  private Long id;
  // 일정 내용
  private String contents;
  // 작성자 ID
  private Long writerId;
  // 비밀번호
  private String password;
  // 작성일
  private String createDate;
  // 수정일
  private String modifyDate;

  public Todo(String contents, Long writerId, String password) {
    this.contents = contents;
    this.writerId = writerId;
    this.password = password;
  }

  public Todo(Long id, String contents, Long writerId, String createDate, String modifyDate) {
    this.id = id;
    this.contents = contents;
    this.writerId = writerId;
    this.createDate = createDate;
    this.modifyDate = modifyDate;
  }
}
