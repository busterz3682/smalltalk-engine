# Smalltalk Engine

[![CI](https://github.com/busterz3682/smalltalk-engine/actions/workflows/ci.yml/badge.svg)](https://github.com/busterz3682/smalltalk-engine/actions/workflows/ci.yml)

상황과 카테고리를 입력하면 대화를 시작할 수 있는 질문 하나를 추천하는 Spring Boot API입니다.

## MVP 기능

- 서버 상태 확인
- 추천용 주제 등록 및 목록 조회
- 상황과 카테고리에 맞춘 스몰토크 질문 추천
- 비용 없는 Fake AI와 실제 OpenAI 전환
- 요청값 검증 및 일관된 오류 응답

## 실행 환경

- Java 21
- Spring Boot 4.1
- Gradle Wrapper

별도의 Gradle 설치는 필요하지 않습니다.

## 로컬 실행

Windows CMD에서 저장소 폴더로 이동합니다.

```cmd
cd /d "%USERPROFILE%\projects\smalltalk-engine"
```

테스트를 실행합니다.

```cmd
gradlew.bat clean test
```

비용이 발생하지 않는 Fake AI로 서버를 실행합니다.

```cmd
gradlew.bat bootRun
```

기본 주소는 `http://localhost:8080`입니다.

## API 사용 예시

서버 상태를 확인합니다.

```cmd
curl -i http://localhost:8080/api/health
```

추천을 요청합니다.

```cmd
curl -i -X POST http://localhost:8080/api/recommendations -H "Content-Type: application/json" -d "{\"situation\":\"회사 점심시간에 처음 만난 동료와 대화\",\"category\":\"일상\"}"
```

정상 응답 예시입니다.

```json
{
  "topicId": null,
  "content": "요즘 새롭게 관심을 갖게 된 취미가 있나요?",
  "situation": "회사 점심시간에 처음 만난 동료와 대화",
  "category": "일상",
  "strategy": "AI"
}
```

## 실제 OpenAI 사용

API 키를 현재 CMD 창에만 임시로 설정합니다.

```cmd
set OPENAI_API_KEY=발급받은_API_키
```

실제 OpenAI 구현을 선택해 실행합니다.

```cmd
gradlew.bat bootRun --args="--app.ai.provider=openai"
```

API 키는 소스 코드, `application.properties`, Git 커밋에 저장하지 않습니다.

## 설정

`src/main/resources/application.properties`의 기본 설정은 다음과 같습니다.

```properties
app.recommendation.strategy=ai
app.ai.provider=fake
app.ai.openai.model=gpt-5.6-luna
```

- `app.recommendation.strategy`: `rule`, `random`, `ai` 중 추천 전략 선택
- `app.ai.provider`: `fake`, `openai` 중 AI 제공자 선택
- `app.ai.openai.model`: 실제 API 호출에 사용할 모델 ID

## 주요 API

| 메서드 | 주소 | 기능 |
|---|---|---|
| `GET` | `/api/health` | 서버 상태 확인 |
| `POST` | `/api/topics` | 추천용 주제 등록 |
| `GET` | `/api/topics` | 등록된 주제 목록 조회 |
| `POST` | `/api/recommendations` | 스몰토크 질문 추천 |

현재 주제 저장소는 메모리 방식이므로 서버를 종료하면 등록한 주제가 사라집니다. AI 추천 전략은 저장된 주제 없이도 동작합니다.

## Docker 실행

Docker 이미지 생성과 실행은 다음 명령으로 확인할 수 있습니다.

```cmd
docker build -t smalltalk-engine:local .
docker run --rm -p 8080:8080 smalltalk-engine:local
```

실제 OpenAI를 사용할 때는 API 키를 이미지에 포함하지 않고 실행 환경변수로 전달합니다.

```cmd
docker run --rm -p 8080:8080 -e OPENAI_API_KEY smalltalk-engine:local --app.ai.provider=openai
```
