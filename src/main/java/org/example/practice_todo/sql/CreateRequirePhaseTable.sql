CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
    contents VARCHAR(50) COMMENT '일정 내용',
    writer_name VARCHAR(20) COMMENT '작성자명',
    password VARCHAR(20) COMMENT '비밀번호',
    create_date DATETIME COMMENT '일정 작성일',
    modify_date DATETIME COMMENT '일정 수정일'
);