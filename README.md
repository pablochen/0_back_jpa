# kakaopay
카카오페이증권 사전과제

# 개발환경
- Java       13.0.2
- Gradle
- SpringBoot 2.2.5
- JPA
- H2 Database
- OpenCsv, Lombok

# 빌드 및 실행
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
