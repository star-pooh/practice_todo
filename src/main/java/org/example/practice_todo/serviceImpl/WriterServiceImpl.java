package org.example.practice_todo.serviceImpl;

import java.util.List;
import org.example.practice_todo.dto.TodoResponseDto;
import org.example.practice_todo.entity.Writer;
import org.example.practice_todo.repository.WriterRepository;
import org.example.practice_todo.service.WriterService;
import org.springframework.stereotype.Service;

@Service
public class WriterServiceImpl implements WriterService {

  private final WriterRepository writerRepository;

  public WriterServiceImpl(WriterRepository writerRepository) {
    this.writerRepository = writerRepository;
  }


  /**
   * 작성자 정보 조회
   *
   * @param writerId 작성자 ID
   * @return 조회된 작성자 정보
   */
  @Override
  public Writer findWriterById(Long writerId) {
    return this.writerRepository.findWriterByIdOrElseThrow(writerId);
  }

  /**
   * 작성자 정보 조회
   *
   * @param writerName 작성자명
   * @return 조회된 작성자 정보
   */
  @Override
  public List<Writer> findWriterByName(String writerName) {
    return this.writerRepository.findWriterByName(writerName);
  }

  /**
   * 응답 데이터 리스트에 작성자명 설정
   *
   * @param todoResponseDtoList 응답 데이터 리스트
   * @return 작성자명이 설정된 응답 데이터 리스트
   */
  @Override
  public List<TodoResponseDto> setWriterName(List<TodoResponseDto> todoResponseDtoList) {
    for (TodoResponseDto todoResponseDto : todoResponseDtoList) {
      Writer writer = this.writerRepository.findWriterByIdOrElseThrow(
          todoResponseDto.getWriterId());
      todoResponseDto.setWriterName(writer.getName());
    }

    return todoResponseDtoList;
  }
}
