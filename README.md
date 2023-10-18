# wanted-pre-onboarding-backend
[과제테스트] 원티드 프리온보딩 10월 백엔드 인턴십

- 이름 : 김유빈

## 목차
- [기술스택](#기술스택)
- [ERD](#ERD)
- [API 설계](#API-설계)

## 기술스택
- Java
- Spring Boot
- JPA
- MySQL

## ERD

![](docs/ERD.png)

## API 설계

### 0. [포스트맨 API 명세서](https://documenter.getpostman.com/view/9434544/2s9YR9XXzd)

### 1. 채용공고 등록

***Request***

```json
POST /jobs
Content-Type: application/json

{
	"companyId" : 1,
	"position" : "백엔드 주니어 개발자",
	"compensation" : 1000000,
	"description" : "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
	"techStack" : "Python"
}
```

| 필드           | 타입      | 설명     |
|--------------|---------|--------|
| companyId    | Integer | 회사 id  |
| position     | String  | 채용 포지션 |
| compensation | Integer | 채용 보상금 |
| description  | String  | 채용 내용  |
| techStack    | String  | 사용 기술  |

***Response***

```json
HTTP/1.1 201 Created
Location: /jobs/1
```

### 2. 채용공고 수정

***Request***

```json
PUT /jobs/1
Content-Type: application/json

{
	"position" : "백엔드 주니어 개발자",
	"compensation" : 1500000,
	"description" : "원티드 랩에서 백엔드 주니어 개발자를 '적극' 채용합니다. 자격요건은..",
	"techStack" : "Python"
}
```

| 필드           | 타입      | 설명     |
|--------------|---------|--------|
| position     | String  | 채용 포지션 |
| compensation | Integer | 채용 보상금 |
| description  | String  | 채용 내용  |
| techStack    | String  | 사용 기술  |

***Response***

```json
HTTP/1.1 200 OK
```

### 3. 채용공고 삭제

***Request***

```json
DELETE /jobs/1
```

***Response***

```json
HTTP/1.1 200 OK
```

### 4. 채용공고 목록 조회

***Request***

```json
GET /jobs?page=1&size=10
```

| 파라미터 | 타입      | 설명                  |
|------|---------|---------------------|
| page | Integer | 화면에 띄울 페이지 (1부터 시작) |
| size | Integer | 화면에 띄울 컨텐츠 개수       |

***Response***

```json
HTTP/1.1 200 OK

{
	"page" : 1,
	"size" : 10,
	"hasNext": false,
	"content" : 
		[
			{
				"jobId" : 1,
				"companyId" : 1,
				"companyName" : "원티드랩",
				"country" : "한국",
				"region" : "서울",
				"position" : "백엔드 주니어 개발자",
				"compensation" : 1500000,
				"techStack" : "Python"
			},
			{
				"jobId" : 2,
				"companyId" : 5,
				"companyName" : "네이버",
				"country" : "한국",
				"region" : "판교",
				"position" : "Django 주니어 개발자",
				"compensation" : 1000000,
				"techStack" : "Django"
			},
			...
		]
}
```

| 필드      | 타입      | 설명                 |
|---------|---------|--------------------|
| page    | Integer | 요청한 페이지            |
| size    | Integer | 요청한 페이지에 띄울 컨텐츠 개수 |
| hasNext | Boolean | 다음 페이지 데이터 존재 여부   |
| content | List    | 데이터 목록             |

| 필드           | 타입      | 설명       |
|--------------|---------|----------|
| jobId        | Integer | 채용 공고 id |
| companyId    | Integer | 회사 id    |
| companyName  | String  | 회사명      |
| country      | String  | 국가       |
| region       | String  | 지역       |
| position     | String  | 채용 포지션   |
| compensation | Integer | 채용 보상금   |
| techStack    | String  | 사용 기술    |

### 5. 채용공고 조회

***Request***

```json
GET /jobs/1
```

***Response***

```json
HTTP/1.1 200 OK

{
  "jobId" : 1,
  "companyId" : 1,
  "companyName" : "원티드랩",
  "country" : "한국",
  "region" : "서울",
  "position" : "백엔드 주니어 개발자",
  "compensation" : 1500000,
  "description" : "원티드 랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..",
  "techStack" : "Python",
  "others" : [
    2, 3, 4, ...
  ]
}
```

| 필드           | 타입      | 설명              |
|--------------|---------|-----------------|
| jobId        | Integer | 채용 공고 id        |
| companyId    | Integer | 회사 id           |
| companyName  | String  | 회사명             |
| country      | String  | 국가              |
| region       | String  | 지역              |
| position     | String  | 채용 포지션          |
| compensation | Integer | 채용 보상금          |
| description  | String  | 채용 내용           |
| techStack    | String  | 사용 기술           |
| others       | List    | 회사가 올린 다른 채용 공고 |

### 6. 채용공고 지원

***Request***

```json
POST /jobs/1/apply
Content-Type: application/json

{
	"userId" : 1
}
```

| 필드     | 타입      | 설명     |
|--------|---------|--------|
| userId | Integer | 사용자 id |

***Response***

```json
HTTP/1.1 200 Ok
```
