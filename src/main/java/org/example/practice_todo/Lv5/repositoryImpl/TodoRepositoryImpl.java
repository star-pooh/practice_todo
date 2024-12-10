package org.example.practice_todo.Lv5.repositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.practice_todo.Lv5.dto.TodoResponseDto;
import org.example.practice_todo.Lv5.entity.Todo;
import org.example.practice_todo.Lv5.repository.TodoRepository;
import org.example.practice_todo.Lv5.util.Paging;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

  private final JdbcTemplate jdbcTemplate;

  public TodoRepositoryImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /**
   * 일정 등록
   *
   * @param todo 요청 데이터로 생성한 일정 정보
   * @return 등록된 일정 정보
   */
  @Override
  public TodoResponseDto createTodo(Todo todo) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
    jdbcInsert.withTableName("todo").usingGeneratedKeyColumns("id");

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = LocalDateTime.now().format(dateTimeFormatter);

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("contents", todo.getContents());
    parameters.put("writer_id", todo.getWriterId());
    parameters.put("password", todo.getPassword());
    parameters.put("create_date", now);
    parameters.put("modify_date", now);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    return new TodoResponseDto(key.longValue(), todo.getContents(), todo.getWriterId(), now, now);
  }

  /**
   * 전체 일정 조회 (작성자명, 수정일 모두 입력이 없는 경우)
   *
   * @param paging 페이징 처리를 위한 정보
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodo(Paging paging) {
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_id, create_date, modify_date "
            + "FROM todo "
            + "ORDER BY modify_date DESC "
            + "LIMIT ? OFFSET ?",
        todoResponseRowMapper(),
        paging.getLimit(),
        paging.getOffset());
  }

  /**
   * 전체 일정 조회 (작성자명만 입력이 있는 경우)
   *
   * @param writerIdList 작성자 ID 리스트
   * @param paging       페이징 처리를 위한 정보
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithWriterId(List<Long> writerIdList, Paging paging) {
    String paramWriterId = String.join(",", Collections.nCopies(writerIdList.size(), "?"));
    String sql = String.format(
        "SELECT id, contents, writer_id, create_date, modify_date "
            + "FROM todo "
            + "WHERE writer_id IN (%s) "
            + "ORDER BY modify_date DESC "
            + "LIMIT ? OFFSET ?",
        paramWriterId
    );

    List<Object> params = new ArrayList<>(writerIdList);
    params.add(paging.getLimit());
    params.add(paging.getOffset());

    return this.jdbcTemplate.query(sql, todoResponseRowMapper(), params.toArray());
  }

  /**
   * 전체 일정 조회 (수정일만 입력이 있는 경우)
   *
   * @param modifyDate 수정일
   * @param paging     페이징 처리를 위한 정보
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate, Paging paging) {
    String paramModifyDate = modifyDate + "%";
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_id, create_date, modify_date "
            + "FROM todo "
            + "WHERE modify_date LIKE ? "
            + "ORDER BY modify_date DESC "
            + "LIMIT ? OFFSET ?",
        todoResponseRowMapper(),
        paramModifyDate,
        paging.getLimit(),
        paging.getOffset());
  }

  /**
   * 전체 일정 조회 (작성자명, 수정일 모두 입력이 있는 경우)
   *
   * @param writerIdList 작성자 ID 리스트
   * @param modifyDate   수정일
   * @param paging       페이징 처리를 위한 정보
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithWriterIdAndModifyDate(
      List<Long> writerIdList, String modifyDate, Paging paging) {
    String paramWriterId = String.join(",", Collections.nCopies(writerIdList.size(), "?"));
    String paramModifyDate = modifyDate + "%";
    String sql = String.format(
        "SELECT id, contents, writer_id, create_date, modify_date "
            + "FROM todo "
            + "WHERE writer_id IN (%s) "
            + "AND modify_date LIKE ? "
            + "ORDER BY modify_date DESC "
            + "LIMIT ? OFFSET ?",
        paramWriterId
    );

    List<Object> params = new ArrayList<>(writerIdList);
    params.add(paramModifyDate);
    params.add(paging.getLimit());
    params.add(paging.getOffset());

    return this.jdbcTemplate.query(sql, todoResponseRowMapper(), params.toArray());
  }

  /**
   * 선택 일정 조회
   *
   * @param id 일정 ID
   * @return 조회된 일정 정보
   */
  @Override
  public Todo findTodoByIdOrElseThrow(Long id) {
    List<Todo> result =
        this.jdbcTemplate.query(
            "SELECT id, contents, writer_id, create_date, modify_date "
                + "FROM todo "
                + "WHERE id = ?",
            todoRowMapper(),
            id);

    return result.stream()
        .findAny()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
  }

  /**
   * 선택 일정 수정
   *
   * @param id       일정 ID
   * @param contents 일정 내용
   * @param password 패스워드
   * @return 수정 결과
   */
  @Override
  public int updateTodo(Long id, String contents, String password) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = LocalDateTime.now().format(dateTimeFormatter);

    return this.jdbcTemplate.update(
        "UPDATE todo "
            + "SET contents = ?, modify_date = ? "
            + "WHERE id = ? "
            + "AND password = ?",
        contents, now, id, password);
  }

  /**
   * 선택 일정 삭제
   *
   * @param id       일정 ID
   * @param password 패스워드
   * @return 삭제 결과
   */
  @Override
  public int deleteTodo(Long id, String password) {
    return this.jdbcTemplate.update(
        "DELETE FROM todo "
            + "WHERE id = ? "
            + "AND password = ?", id, password
    );
  }

  /**
   * 전체 레코드 수 조회(작성자명, 수정일 모두 입력이 없는 경우)
   *
   * @return 전체 레코드 수
   */
  @Override
  public long countTotalTodo() {
    return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM todo", Long.class);
  }

  /**
   * 전체 레코드 수 조회(작성자명만 입력이 있는 경우)
   *
   * @param writerId 작성자 ID
   * @return 전체 레코드 수
   */
  @Override
  public long countTotalTodoWithWriterId(Long writerId) {
    return this.jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM todo WHERE writer_id = ?",
        Long.class, writerId);
  }

  /**
   * 전체 레코드 수 조회(수정일만 입력이 있는 경우)
   *
   * @param modifyDate 수정일
   * @return 전체 레코드 수
   */
  @Override
  public long countTotalTodoWithModifyDate(String modifyDate) {
    return this.jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM todo WHERE modify_date = ?",
        Long.class, modifyDate);
  }

  /**
   * 전체 레코드 수 조회(작성자명, 수정일 모두 입력이 있는 경우)
   *
   * @param writerId   작성자 ID
   * @param modifyDate 수정일
   * @return 전체 레코드 수
   */
  @Override
  public long countTotalTodoWithWriterIdAndModifyDate(Long writerId, String modifyDate) {
    return this.jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM todo WHERE writer_id = ? AND modify_date = ?",
        Long.class, writerId, modifyDate);
  }


  private RowMapper<TodoResponseDto> todoResponseRowMapper() {
    return new RowMapper<TodoResponseDto>() {
      @Override
      public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TodoResponseDto(
            rs.getLong("id"),
            rs.getString("contents"),
            rs.getLong("writer_id"),
            rs.getString("create_date"),
            rs.getString("modify_date"));
      }
    };
  }

  private RowMapper<Todo> todoRowMapper() {
    return new RowMapper<Todo>() {
      @Override
      public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Todo(
            rs.getLong("id"),
            rs.getString("contents"),
            rs.getLong("writer_id"),
            rs.getString("create_date"),
            rs.getString("modify_date"));
      }
    };
  }
}
