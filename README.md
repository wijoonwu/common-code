# Common Code API

## 요구사항
> 공통 코드는 한글 명과 코드 값으로 이루어져 있습니다.<br>
> 각 공통 코드는 그룹을 가지고 있어 그룹으로 조회 할 수 있고, 개별 조회도 가능합니다.


## 개발환경
- JDK `11`
- Gradle `7.6.1`
- Spring-boot `2.7.12`
- H2 Database
- JPA

## DB 다이어그램
~~~bash
+-----------------+      +------------------+
|    CodeGroup    |      |    CommonCode    |
+-----------------+      +------------------+
|       id        |      |        id        |
|      name       |      |       code       |
+-----------------+      |       name       |
                         |   code_group_id  |
                         +------------------+
~~~

## 디렉터리 구조
```bash
└── src/main/java/company/ejm/commoncode/api
    ├── controller
    │   └── CommonCodeController.java
    ├── dto
    │   ├── CodeGroupDto.java
    │   ├── CommonCodeDto.java
    │   └── ErrorDto.java
    ├── entity
    │   ├── CodeGroup.java
    │   └── CommonCode.java
    ├── exception
    │   ├── CustomException.java
    │   └── GlobalExceptionHandler.java
    ├── model
    │   ├── ErrorCode.java
    │   ├── Message.java
    │   └── StatusEnum.java
    ├── repository
    │   ├── CodeGroupRepository.java
    │   └── CommonCodeRepository.java
    └── service
        └── CommonCodeService.java

```


## 프로젝트 설치

### MacOS

```bash
git clone https://github.com/wijoonwu/common-code.git
cd common-code
./gradlew bootRun
```

### Windows

```bash
git clone https://github.com/wijoonwu/common-code.git
cd common-code
gradlew bootRun
```

## 실행 방법

`Postman` 혹은 `Talend API` 등의 프로그램을 이용해서 아래와 같이 테스트 합니다.

### 코드 그룹 생성

- Method: `POST`
- Url: http://localhost:8080/api/common-codes/group

#### Request

```bash
{
    "name" : "group1"
}
```

#### Response

```bash
{
    "code": 201,
    "message": "Group creation successful.",
    "data": {
        "id": 1,
        "name": "group1",
        "commonCodeList": []
    }
}
```

### 💡 그룹 이름 중복 체크

> 중복된 group name 이 있는 경우 아래와 같이 409 응답 코드와 함께 오류 메시지가 전달 됩니다.

#### Response

```bash
{
    "status": 409,
    "message": "이미 존재하는 그룹 이름입니다."
}
```

### 💡 공백 입력 불가

> 제목 혹은 내용이 `""`, `" "`와 같이 공란이거나 `null`인 경우 게시글 작성이 불가하며 `400` 오류가 발생합니다.

#### Response

```bash
{
    "status": 400,
    "message": "공백은 입력할 수 없습니다."
}
```

### 공통 코드 생성

- Method: `POST`
- Url: http://localhost:8080/api/common-codes

#### Request

```bash
{
  "code": "3005",
  "name": "데이터 검증 오류",
  "groupName": "group1"
}
```

#### Response

```bash
{
    "code": 201,
    "message": "Code creation successful.",
    "data": {
        "id": 2,
        "code": "3005",
        "name": "데이터 검증 오류",
        "groupName": "group1"
    }
}
```

### 💡 존재하지 않는 그룹을 지정했을 때

#### Response
```bash
{
    "status": 400,
    "message": "존재하지 않는 그룹입니다."
}
```

### 코드 그룹 조회

- Method: `GET`
- Url: http://localhost:8080/api/common-codes/groups

#### Response

```bash
{
    "code": 200,
    "message": "Group retrieval successful.",
    "data": [
        {
            "id": 1,
            "name": "group1",
            "commonCodeList": [
                {
                    "id": 2,
                    "code": "3005",
                    "name": "데이터 검증 오류",
                    "groupName": "group1"
                }
            ]
        },
        {
            "id": 3,
            "name": "group2",
            "commonCodeList": []
        }
    ]
}
```

### 특정 코드 그룹 조회

- Method: `GET`
- Url: http://localhost:8080/api/common-codes/group/{groupName}

#### Response

```bash
{
    "code": 200,
    "message": "Group codes retrieval successful.",
    "data": [
        {
            "id": 2,
            "code": "3005",
            "name": "데이터 검증 오류",
            "groupName": "testGroup"
        },
        {
            "id": 4,
            "code": "3006",
            "name": "사용자 인증 실패",
            "groupName": "testGroup"
        }
    ]
}
```

### 특정 공통 코드 조회

- Method: `GET`
- Url: http://localhost:8080/api/common-codes/{codeId}

#### Response

```bash
{
    "code": 200,
    "message": "Code retrieval successful.",
    "data": {
        "id": 2,
        "code": "3005",
        "name": "데이터 검증 오류",
        "groupName": "group1"
    }
}
```

### 코드 그룹 이름 수정
- Method: `PUT`
- Url: http://localhost:8080/api/common-codes/group/{groupId}

#### Request

```bash
{
    "name" : "testGroup"
}
```

#### Response

```bash
{
    "code": 200,
    "message": "Group name update successful.",
    "data": {
        "id": 1,
        "name": "testGroup",
        "commonCodeList": [
            {
                "id": 2,
                "code": "3005",
                "name": "데이터 검증 오류",
                "groupName": "testGroup"
            }
        ]
    }
}
```

### 공통 코드 정보 수정

- Method: `PUT`
- Url: http://localhost:8080/api/common-codes/{codeId}


#### Request

```bash
{
    "code" : "3006",
    "name" : "데이터 검증 실패",
    "groupName" : "group2"
}
```
#### Response

```bash
{
    "code": 200,
    "message": "Code update successful.",
    "data": {
        "id": 2,
        "code": "3006",
        "name": "데이터 검증 실패",
        "groupName": "group2"
    }
}
```

### 공통 코드 삭제

- Method: `DELETE`
- Url: http://localhost:8080/api/common-codes/{codeId}

#### Response

```bash
{
    "code": 200,
    "message": "Code deletion successful.",
    "data": null
}
```

### 코드 그룹 내 공통 코드 전체 삭제

- Method: `DELETE`
- Url: http://localhost:8080/api/common-codes/group/{groupId}

#### Response

```bash
{
    "code": 200,
    "message": "Codes deletion in group successful.",
    "data": null
}
```


### 💡 삭제된 그룹 혹은 코드를 조회하는 경우

> 삭제된 그룹 혹은 코드를 조회하는 경우, 아래와 같은 오류가 응답됩니다.

#### Response

```bash
{
    "status": 400,
    "message": "존재하지 않는 코드입니다."
}
```
```bash
{
    "status": 400,
    "message": "존재하지 않는 그룹입니다."
}
```

