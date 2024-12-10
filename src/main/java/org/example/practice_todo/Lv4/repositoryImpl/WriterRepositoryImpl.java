package org.example.practice_todo.Lv4.repositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.example.practice_todo.Lv4.entity.Writer;
import org.example.practice_todo.Lv4.repository.WriterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class WriterRepositoryImpl implements WriterRepository {

  private final JdbcTemplate jdbcTemplate;

  public WriterRepositoryImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  /**
   * 작성자 정보 조회
   *
   * @param writerId 작성자 ID
   * @return 조회된 작성자 정보
   */
  @Override
  public Writer findWriterByIdOrElseThrow(Long writerId) {
    List<Writer> result =
        this.jdbcTemplate.query(
            "SELECT * FROM writer WHERE id = ?",
            writerRowMapper(),
            writerId);

    return result.stream()
        .findAny()
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Does not exists id = " + writerId));
  }

  /**
   * 작성자 정보 조회
   *
   * @param writerName 작성자명
   * @return 조회된 작성자 정보
   */
  @Override
  public List<Writer> findWriterByName(String writerName) {
    return this.jdbcTemplate.query(
        "SELECT * FROM writer WHERE name = ?",
        writerRowMapper(),
        writerName);
  }

  private RowMapper<Writer> writerRowMapper() {
    return new RowMapper<Writer>() {
      @Override
      public Writer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Writer(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("create_date"),
            rs.getString("modify_date"));
      }
    };
  }
}
