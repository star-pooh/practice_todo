package org.example.practice_todo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Todo;
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

  @Override
  public List<TodoResponseDto> findAllTodo() {
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo ORDER BY modify_date DESC",
        todoResponseRowMapper());
  }

  @Override
  public List<TodoResponseDto> findAllTodoWithWriterName(String writerName) {
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE writer_name = ? ORDER BY modify_date DESC",
        todoResponseRowMapper(),
        writerName);
  }

  @Override
  public List<TodoResponseDto> findAllTodoWithModifyDate(String modifyDate) {
    String paramModifyDate = modifyDate + "%";
    return this.jdbcTemplate.query(
        "SELECT id, contents, writer_name, create_date, modify_date FROM todo WHERE modify_date LIKE ? ORDER BY modify_date DESC",
        todoResponseRowMapper(),
        paramModifyDate);
  }

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
