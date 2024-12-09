package org.example.practice_todo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {

  // 일정 내용
  @NotNull
  @Size(max = 200)
  private String contents;
  // 작성자 ID
  private Long writerId;
  // 작성자명
  private String writerName;
  // 비밀번호
  @NotNull
  private String password;
}
