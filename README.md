# 카카오페이증권 사전과제


## 개발환경
- Java       13.0.2
- Gradle
- SpringBoot 2.2.5
- JPA
- H2 Database
- OpenCsv, Lombok, Etc

## 빌드 및 실행
- Intellij (for Window mode)
  - File > New > Project From Existing Sources > build.gradle
  - File > Settings > Build, Execution, Deployment > Gradle > Build and run using & Run tests using - Check
  - File > Settings > Build, Execution, Deployment > Annotation Processors > Enable Annotation processing - Check
  - src > main > java > subcject.finance > FinanceApplication.java - Start
- Runnable Jar
  - {unzipped dir} > gradlew build && java -jar build/libs/finance-0.0.1-SNAPSHOT.jar
- H2 inmemory database (after start jar)
  - http://localhost:8080/h2-console
- Swagger (after start jar)
  - http://localhost:8080/swagger-ui.html

## 요구사항
  - [x] 모든 API 작성
  - [x] SpringBoot Framework 사용
  - [x] 단위 유닛 테스트 작성 - Controller, Service
  - [x] JSON 입출력
  - [x] Inmemory Database을 통한 테이블 생성 및 데이터 입력
  - [x] README.md 작성

## 문제 해결 방법
- 0) Inmemory DB 테이블 생성 및 데이터 입력
  - Stategy
  - Request
  - Response
  - ETC
  
- 1) 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객 추출(취소거래 제외)
  - Stategy
  - Request
  - Response
  - ETC
  
- 2) 2018년 또는 2019년에 거래가 없는 고객을 추출(취소거래 제외)
  - Stategy
  - Request
  - Response
  - ETC
  
- 3) 연도별 관리점별 거래금액 합계 출력 및 거래금액 기준 내림차순 정렬(취소거래 제외)
  - Stategy
  - Request
  - Response
  - ETC
  
- 4) 분당점을 판교점으로 이관 후 입력한 지점의 정보와 거래금액 출력(취소거래 제외)
  - Stategy
  - Request
  - Response
  - ETC
