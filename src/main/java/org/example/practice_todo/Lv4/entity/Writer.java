package org.example.practice_todo.Lv4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Writer {

  // 작성자 ID
  private Long id;
  // 작성자명
  private String name;
  // 이메일
  private String email;
  // 등록일
  private String createDate;
  // 수정일
  private String modifyDate;
}
