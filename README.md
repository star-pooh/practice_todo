## 일정 관리 앱

------

### 프로젝트 소개

간단한 일정을 관리할 수 있는 프로젝트입니다.

View가 없기 때문에 Postman으로만 동작 확인이 가능하며, 상세 내용은 아래 `API 명세서`에 작성되어 있습니다.

------

### 기술 스택

<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

------

### API 명세서

`POST` /api/todos &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일정 생성

<details>
<summary>상세 내용</summary>

RequestBody

    "contents": "일정990",
    "writerId": 11,
    "password": "1234"

ResponseBody

    "id": 38,
    "contents": "일정99990",
    "writerId": 11,
    "writerName": "사용자10",
    "createDate": "2024-12-09 23:54:41",
    "modifyDate": "2024-12-09 23:54:41"

Error Message

    "message": error message,
    "status": status code

</details>
<br>

`GET` /api/todos
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일정 전체 조회

<details>
<summary>상세 내용</summary>

Query Params (필수값 없음)

    pageNumber : 페이지 번호 (기본값 = 1)
    pageSize : 페이지 크기 (기본값 = 10)
    writerName : 작성자명
    modifyDate : 수정일

ResponseBody

    [
        {
            "id": 33,
            "contents": "일정99",
            "writerId": 10,
            "writerName": "사용자10",
            "createDate": "2024-12-09 14:06:51",
            "modifyDate": "2024-12-09 14:06:51"
        },
        {
            ...
        },
        {
            ...
        }
    ]

페이지 범위를 벗어난 요청의 경우

    []

</details>
<br>

`GET` /api/todos/{id} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일정 1건 조회

<details>
<summary>상세 내용</summary>

ResponseBody

    {
        "id": 30,
        "contents": "일정30",
        "writerId": 6,
        "writerName": "사용자6",
        "createDate": "2024-12-08 17:00:01",
        "modifyDate": "2024-12-08 17:00:01"
    }

Error Message

    "message": error message,
    "status": status code

</details>
<br>

`PATCH` /api/todos/{id} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일정 수정

<details>
<summary>상세 내용</summary>

RequestBody

    {
        "contents": "수정된 일정31",
        "writerName": "사용자31",
        "password": "1234"
    }

ResponseBody

    {
        "id": 35,
        "contents": "수정된 일정31",
        "writerId": 11,
        "writerName": "사용자10",
        "createDate": "2024-12-09 14:58:50",
        "modifyDate": "2024-12-10 00:18:42"
    }

Error Message

    "message": error message,
    "status": status code

</details>
<br>

`DELETE` /api/todos/{id} &nbsp;&nbsp;&nbsp;&nbsp;일정 삭제

<details>
<summary>상세 내용</summary>

RequestBody

    {
        "password" : "1234"
    }

Error Message

    "message": error message,
    "status": status code

</details>

------

### ERD

https://www.erdcloud.com/d/MJRpsvsuhwbeAc4wu

![image](https://github.com/user-attachments/assets/208b1765-133d-42fd-a073-d6264c2827ad)

------

### 프로젝트 구조

    📦java
    ┗ 📂org
    ┃ ┗ 📂example
    ┃ ┃ ┗ 📂practice_todo
    ┃ ┃ ┃ ┣ 📂controller
    ┃ ┃ ┃ ┃ ┗ 📜TodoController.java
    ┃ ┃ ┃ ┣ 📂dto
    ┃ ┃ ┃ ┃ ┣ 📜TodoRequestDto.java
    ┃ ┃ ┃ ┃ ┗ 📜TodoResponseDto.java
    ┃ ┃ ┃ ┣ 📂entity
    ┃ ┃ ┃ ┃ ┣ 📜Todo.java
    ┃ ┃ ┃ ┃ ┗ 📜Writer.java
    ┃ ┃ ┃ ┣ 📂exception
    ┃ ┃ ┃ ┃ ┗ 📜GlobalExceptionHandler.java
    ┃ ┃ ┃ ┣ 📂repository
    ┃ ┃ ┃ ┃ ┣ 📜TodoRepository.java
    ┃ ┃ ┃ ┃ ┗ 📜WriterRepository.java
    ┃ ┃ ┃ ┣ 📂repositoryImpl
    ┃ ┃ ┃ ┃ ┣ 📜TodoRepositoryImpl.java
    ┃ ┃ ┃ ┃ ┗ 📜WriterRepositoryImpl.java
    ┃ ┃ ┃ ┣ 📂service
    ┃ ┃ ┃ ┃ ┣ 📜TodoService.java
    ┃ ┃ ┃ ┃ ┗ 📜WriterService.java
    ┃ ┃ ┃ ┣ 📂serviceImpl
    ┃ ┃ ┃ ┃ ┣ 📜TodoServiceImpl.java
    ┃ ┃ ┃ ┃ ┗ 📜WriterServiceImpl.java
    ┃ ┃ ┃ ┣ 📂sql
    ┃ ┃ ┃ ┃ ┣ 📜CreateChallengePhaseTable.sql
    ┃ ┃ ┃ ┃ ┣ 📜CreateRequirePhaseTable.sql
    ┃ ┃ ┃ ┃ ┣ 📜InsertChallengePhaseInitData.sql
    ┃ ┃ ┃ ┃ ┗ 📜InsertRequirePhaseInitData.sql
    ┃ ┃ ┃ ┣ 📂util
    ┃ ┃ ┃ ┃ ┗ 📜Paging.java
    ┃ ┃ ┃ ┗ 📜PracticeTodoApplication.java

------

### 만들면서 신경썼던 점 및 느낀 점

1. 각 파일에서 일관된 기능만 수행할 수 있도록 구현했습니다.
2. 요구 사항 가이드에 있는 내용을 최대한 구현하려고 했습니다.
3. Javadoc 및 주석을 빼먹지 않으려고 했습니다.
4. `1.`을 지키기 위해 테이블 별로 Service와 Repository를 나눴더니 데이터 간 결합이 어려웠습니다.
    1. Join을 사용하게 되면 필요한 데이터를 가져오기 위해 하나의 파일에서 두 개의 테이블에 접근하는 상태가 됩니다.
    2. Join을 사용하지 않아서 `1.`은 지켰지만, 데이터베이스에 접근하는 횟수가 늘어났다고 생각합니다.
    3. 지금은 테이블이 2개뿐이라서 큰 문제가 없지만 테이블이 많아지게 된다면 성능면에서 좋지 않을 수 있다는 생각이 들었으며,

       쿼리의 복잡도에 따라 다르겠지만 어느 정도의 접근 횟수가 적절한 지에 대한 궁금증이 생겼습니다.