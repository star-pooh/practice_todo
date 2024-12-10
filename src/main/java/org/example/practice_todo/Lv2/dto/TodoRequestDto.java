package org.example.practice_todo.Lv2.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {

  // 일정 내용
  private String contents;
  // 작성자명
  private String writerName;
  // 비밀번호
  private String password;
}
