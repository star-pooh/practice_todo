DROP TABLE todo;

CREATE TABLE writer (
    id BIGINT PRIMARY KEY COMMENT '작성자 ID',
    name VARCHAR(20) COMMENT '작성자명',
    email VARCHAR(20) COMMENT '이메일',
    create_date DATETIME COMMENT '작성자 등록일',
    modify_date DATETIME COMMENT '작성자 수정일'
);

CREATE TABLE todo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 ID',
    contents VARCHAR(200) NOT NULL COMMENT '일정 내용',
    writer_id BIGINT COMMENT '작성자 ID',
    password VARCHAR(20) NOT NULL COMMENT '비밀번호',
    create_date DATETIME COMMENT '일정 작성일',
    modify_date DATETIME COMMENT '일정 수정일',
    FOREIGN KEY (writer_id) REFERENCES writer(id)
);