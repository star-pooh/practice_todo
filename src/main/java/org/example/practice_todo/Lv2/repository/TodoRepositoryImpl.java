package org.example.practice_todo.Lv2.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.practice_todo.Lv2.dto.TodoResponseDto;
import org.example.practice_todo.Lv2.entity.Todo;
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
    parameters.put("writer_name", todo.getWriterName());
    parameters.put("password", todo.getPassword());
    parameters.put("create_date", now);
    parameters.put("modify_date", now);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    return new TodoResponseDto(key.longValue(), todo.getContents(), todo.getWriterName(), now, now);
  }

  /**
   * 전체 일정 조회 (작성자명, 수정일 모두 입력이 없는 경우)
   *
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodo() {
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo ORDER BY modify_date DESC",
        todoResponseRowMapper());
  }

  /**
   * 전체 일정 조회 (작성자명만 입력이 있는 경우)
   *
   * @param writerName 작성자명
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithWriterName(String writerName) {
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE writer_name = ? ORDER BY modify_date DESC",
        todoResponseRowMapper(),
        writerName);
  }

  /**
   * 전체 일정 조회 (수정일만 입력이 있는 경우)
   *
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate) {
    String paramModifyDate = modifyDate + "%";

    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE modify_date LIKE ? ORDER BY modify_date DESC",

        todoResponseRowMapper(),
        paramModifyDate);
  }

  /**
   * 전체 일정 조회 (작성자명, 수정일 모두 입력이 있는 경우)
   *
   * @param writerName 작성자명
   * @param modifyDate 수정일
   * @return 조회된 일정 정보
   */
  @Override
  public List<TodoResponseDto> findAllTodoWithWriterNameAndModifyDate(
      String writerName, String modifyDate) {
    String paramModifyDate = modifyDate + "%";
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE writer_name = ? AND modify_date LIKE ? ORDER BY modify_date DESC",
        todoResponseRowMapper(),
        writerName,
        paramModifyDate);
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
            "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE id = ?",
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
   * @param id         일정 ID
   * @param contents   일정 내용
   * @param writerName 작성자명
   * @param password   패스워드
   * @return 수정 결과
   */
  @Override
  public int updateTodo(Long id, String contents, String writerName, String password) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String now = LocalDateTime.now().format(dateTimeFormatter);

    return this.jdbcTemplate.update(
        "UPDATE todo SET contents = ?, writer_name = ?, modify_date = ? WHERE id = ? AND password = ?",
        contents, writerName, now, id, password);
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
        "DELETE FROM todo WHERE id = ? AND password = ?", id, password
    );
  }

  private RowMapper<TodoResponseDto> todoResponseRowMapper() {
    return new RowMapper<TodoResponseDto>() {
      @Override
      public TodoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new TodoResponseDto(
            rs.getLong("id"),
            rs.getString("contents"),
            rs.getString("writer_name"),
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
            rs.getString("writer_name"),
            rs.getString("create_date"),
            rs.getString("modify_date"));
      }
    };
  }
}
