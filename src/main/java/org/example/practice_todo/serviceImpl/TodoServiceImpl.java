package org.example.practice_todo.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.example.practice_todo.dto.TodoRequestDto;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Todo;
import org.example.practice_todo.entity.Writer;
import org.example.practice_todo.repository.TodoRepository;
import org.example.practice_todo.service.TodoService;
import org.example.practice_todo.util.Paging;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TodoServiceImpl implements TodoService {

  // 일정 관리 앱 레포지토리
  private final TodoRepository todoRepository;

  public TodoServiceImpl(TodoRepository todoRepository) {
    this.todoRepository = todoRepository;
  }

  /**
   * 일정 생성 및 등록
   *
   * @param dto 등록에 사용할 요청 데이터
   * @return 등록된 일정 정보
   */
  @Override
  public TodoResponseDto createTodo(TodoRequestDto dto) {
    Todo todo = new Todo(dto.getContents(), dto.getWriterId(), dto.getPassword());
    return this.todoRepository.createTodo(todo);
  }

  /**
   * 전체 일정 조회
   * <p>
   * 작성자명, 수정일의 입력에 따라 조회 결과가 다름
   *
   * @param writerList 작성자 정보 리스트
   * @param modifyDate 수정일
   * @param pageNumber 페이지 번호
   * @param pageSize   페이지 크기
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodo(List<Writer> writerList, String modifyDate,
      int pageNumber, int pageSize) {
    List<TodoResponseDto> todoResponseDtoList = new ArrayList<>();
    // 작성자명의 입력이 없는 경우
    if (writerList.isEmpty() && Objects.isNull(modifyDate)) {
      todoResponseDtoList.addAll(getAllTodo(pageNumber, pageSize));
    } else if (writerList.isEmpty() && !Objects.isNull(modifyDate)) {
      todoResponseDtoList.addAll(getAllTodoWithModifyDate(pageNumber, pageSize, modifyDate));
    } else {
      // 작성자명의 입력이 있는 경우
      if (Objects.isNull(modifyDate)) {
        todoResponseDtoList.addAll(
            getAllTodoWithWriterId(pageNumber, pageSize, writerList));
      } else {
        todoResponseDtoList.addAll(
            getAllTodoWithWriterIdAndModifyDate(pageNumber, pageSize, writerList, modifyDate));
      }
    }
    todoResponseDtoList.sort(Comparator.comparing(TodoResponseDto::getModifyDate).reversed());
    return todoResponseDtoList;
  }

  /**
   * 선택 일정 조회
   * <p>
   * 일정 ID로 조회
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  @Override
  public TodoResponseDto findTodoById(Long id) {
    Todo todo = this.todoRepository.findTodoByIdOrElseThrow(id);
    return new TodoResponseDto(todo);
  }

  /**
   * 선택 일정 수정
   * <p>
   * 일정 ID와 패스워드로 조회하며 일정 내용만 수정
   *
   * @param id  일정 ID
   * @param dto 수정에 사용할 요청 데이터
   * @return 수정된 일정 정보
   */
  @Transactional
  @Override
  public TodoResponseDto updateTodo(Long id, TodoRequestDto dto) {
    if (Objects.isNull(dto.getContents()) || Objects.isNull(
        dto.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "contents and password are required values.");
    }

    int updatedRow = this.todoRepository.updateTodo(id, dto.getContents(), dto.getPassword());

    if (updatedRow == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID or Password does not match.");
    }

    Todo todo = this.todoRepository.findTodoByIdOrElseThrow(id);
    return new TodoResponseDto(todo);
  }

  /**
   * 선택 일정 삭제
   * <p>
   * 일정 ID와 패스워드로 조회하며 해당 일정 삭제
   *
   * @param id  일정 ID
   * @param dto 삭제에 사용할 요청 데이터
   */
  @Transactional
  @Override
  public void deleteTodo(Long id, TodoRequestDto dto) {
    if (Objects.isNull(dto.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required value.");
    }

    int deletedRow = this.todoRepository.deleteTodo(id, dto.getPassword());

    if (deletedRow == 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID or Password does not match.");
    }
  }

  /**
   * 전체 일정 조회
   * <p>
   * 작성자명, 수정일 모두 입력이 없는 경우
   *
   * @param pageNumber 페이지 번호
   * @param pageSize   페이지 크기
   * @return 조회된 일정 정보
   */
  private List<TodoResponseDto> getAllTodo(int pageNumber, int pageSize) {
    long totalRecords = this.todoRepository.countTotalTodo();
    Paging paging = createPaging(pageNumber, pageSize, totalRecords);

    // 페이지 범위를 벗어난 요청에 대해서는 빈 값을 반환
    if (!Objects.isNull(paging)) {
      return this.todoRepository.findAllTodo(paging);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 전체 일정 조회
   * <p>
   * 수정일만 입력이 있는 경우
   *
   * @param pageNumber 페이지 번호
   * @param pageSize   페이지 크기
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  private List<TodoResponseDto> getAllTodoWithModifyDate(int pageNumber, int pageSize,
      String modifyDate) {
    long totalRecords = this.todoRepository.countTotalTodoWithModifyDate(modifyDate);
    Paging paging = createPaging(pageNumber, pageSize, totalRecords);

    // 페이지 범위를 벗어난 요청에 대해서는 빈 값을 반환
    if (!Objects.isNull(paging)) {
      return this.todoRepository.findAllTodoWithModifyDate(modifyDate, paging);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 전체 일정 조회
   * <p>
   * 작성자명만 입력이 있는 경우
   *
   * @param pageNumber 페이지 번호
   * @param pageSize   페이지 크기
   * @param writerList 작성자 정보 리스트
   * @return 조회된 일정 정보
   */
  private List<TodoResponseDto> getAllTodoWithWriterId(int pageNumber, int pageSize,
      List<Writer> writerList) {
    List<Long> writerIdList = writerList.stream().map(Writer::getId).toList();
    long totalRecordNum = getTotalRecordNum(writerList, null, false);
    Paging paging = createPaging(pageNumber, pageSize, totalRecordNum);

    // 페이지 범위를 벗어난 요청에 대해서는 빈 값을 반환
    if (!Objects.isNull(paging)) {
      return this.todoRepository.findAllTodoWithWriterId(writerIdList, paging);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 전체 일정 조회
   * <p>
   * 작성자명, 수정일 모두 입력이 있는 경우
   *
   * @param pageNumber 페이지 번호
   * @param pageSize   페이지 크기
   * @param writerList 작성자 정보 리스트
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  private List<TodoResponseDto> getAllTodoWithWriterIdAndModifyDate(int pageNumber, int pageSize,
      List<Writer> writerList, String modifyDate) {
    List<Long> writerIdList = writerList.stream().map(Writer::getId).toList();
    long totalRecordNum = getTotalRecordNum(writerList, modifyDate, true);
    Paging paging = createPaging(pageNumber, pageSize, totalRecordNum);

    // 페이지 범위를 벗어난 요청에 대해서는 빈 값을 반환
    if (!Objects.isNull(paging)) {
      return this.todoRepository.findAllTodoWithWriterIdAndModifyDate(writerIdList, modifyDate,
          paging);
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * 페이징 객체 생성
   *
   * @param pageNumber   페이지 번호
   * @param pageSize     페이지 크기
   * @param totalRecords 총 레코드 수
   * @return 페이징 객체
   */
  private Paging createPaging(int pageNumber, int pageSize, long totalRecords) {
    Paging paging = new Paging(pageNumber, pageSize, totalRecords);

    // 범위를 넘어선 페이지를 요청하는 경우 빈 객체를 반환
    if (paging.isPageOutOfRange()) {
      return null;
    }
    return paging;
  }

  /**
   * 작성자명, 수정일의 입력에 따른 총 레코드 수를 반환
   *
   * @param writerList    작성자 정보 리스트
   * @param modifyDate    수정일
   * @param hasModifyDate 수정일 포함 여부
   * @return 총 레코드 수
   */
  private long getTotalRecordNum(List<Writer> writerList, String modifyDate,
      boolean hasModifyDate) {
    long totalRecordNum = 0;

    for (Writer writer : writerList) {
      if (hasModifyDate) {
        totalRecordNum += this.todoRepository.countTotalTodoWithWriterIdAndModifyDate(
            writer.getId(), modifyDate);
      } else {
        totalRecordNum += this.todoRepository.countTotalTodoWithWriterId(writer.getId());
      }
    }
    return totalRecordNum;
  }

}
