package org.example.practice_todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {
  private String contents;
  private String writerName;
  private String password;
}
