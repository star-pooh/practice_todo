package org.example.practice_todo.Lv5.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Paging {

  // 페이지 번호
  private int pageNumber;
  // 페이지 크기
  private int pageSize;
  // 총 레코드 수
  private long totalRecords;

  public int getOffset() {
    return (pageNumber - 1) * pageSize;
  }

  public int getLimit() {
    return pageSize;
  }

  /**
   * 범위를 벗어난 페이지 요청인지 확인
   *
   * @return true : 범위를 벗어남 / false : 범위를 벗어나지 않음
   */
  public boolean isPageOutOfRange() {
    return pageNumber > (int) Math.ceil((double) totalRecords / pageNumber);
  }
}
