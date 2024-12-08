package org.example.practice_todo.entity;

import lombok.Getter;

@Getter
public class Todo {

  // ID
  private Long id;
  // 일정 내용
  private String contents;
  // 작성자명
  private String writerName;
  // 비밀번호
  private String password;
  // 생성일
  private String createDate;
  // 수정일
  private String modifyDate;

  public Todo(String contents, String writerName, String password) {
    this.contents = contents;
    this.writerName = writerName;
    this.password = password;
  }

  public Todo(Long id, String contents, String writerName, String createDate, String modifyDate) {
    this.id = id;
    this.contents = contents;
    this.writerName = writerName;
    this.createDate = createDate;
    this.modifyDate = modifyDate;
  }
}
