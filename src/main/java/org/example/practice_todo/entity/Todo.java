package org.example.practice_todo.entity;

import lombok.Getter;

@Getter
public class Todo {
  private Long id;
  private String contents;
  private String writerName;
  private String password;
  private String createDate;
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
