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
- 0 ) Inmemory DB 테이블 생성 및 데이터 입력
  - Stategy
    - H2 Inmemory DB와 Hibernate를 사용하여 테이블 생성
    - 파일을 InputStream으로 읽어 Runnable Jar에서도 Data Insert 가능하게 개발
  - Request
    ```
    http://localhost:8080/init/database`
    ```
  - Response
    ```
    {
      "200": "database init success"
    }
    ```
  - ETC
    - N/A
- 1 ) 2018년, 2019년 각 연도별 합계 금액이 가장 많은 고객 추출(취소거래 제외)
  - Stategy
    - 연도를 입력받아 해당 연도의 합계 금액이 가장 많은 고객을 추출
    - 입력은 GET Method를 사용하며 단일년도 혹은 다수의 년도 입력 가능
  - Request
    ```
    http://localhost:8080/subject/maxSumAmtAcct?years=2018,2019
    ```
  - Response
    ```
    [
      {
        "year": "2018",
        "name": "테드",
        "acctNo": "11111114",
        "sumAmt": 28992000
      },
      {
        "year": "2019",
        "name": "에이스",
        "acctNo": "11111112",
        "sumAmt": 40998400
      }
    ]
    ```
  - ETC
    - 잘못된 케이스 입력 시, 오류 처리
    ```
    http://localhost:8080/subject/maxSumAmtAcct?years=2011
    ```
    ```
    {
      "404": "acct no not found error"
    }
    ```
  
- 2 ) 2018년 또는 2019년에 거래가 없는 고객을 추출(취소거래 제외)
  - Stategy
    - 연도를 입력받아 해당 연도에 거래가 없는 고객을 추출
    - 입력은 GET Method를 사용하며 단일년도 혹은 다수의 년도 입력 가능
  - Request
    ```
    http://localhost:8080/subject/notTrxAcct?years=2018,2019
    ```
  - Response
    ```
    [
      {
        "year": "2018",
        "name": "사라",
        "acctNo": "11111115"
      },
      {
        "year": "2018",
        "name": "에이스",
        "acctNo": "11111121"
      },
      {
        "year": "2019",
        "name": "테드",
        "acctNo": "11111114"
      },
      {
        "year": "2019",
        "name": "에이스",
        "acctNo": "11111121"
      }
    ]
    ```
  - ETC
    - 거래가 없는 년도 입력 시, 모든 계정 출력
    ```
    http://localhost:8080/subject/notTrxAcct?years=2027
    ```
    ```
    [
      {
        "year": "2027",
        "name": "제이",
        "acctNo": "11111111"
      },
      {
        "year": "2027",
        "name": "에이스",
        "acctNo": "11111112"
      },
      {
        "year": "2027",
        "name": "리노",
        "acctNo": "11111113"
      },
      {
        "year": "2027",
        "name": "테드",
        "acctNo": "11111114"
      },
      {
        "year": "2027",
        "name": "사라",
        "acctNo": "11111115"
      },
      {
        "year": "2027",
        "name": "린",
        "acctNo": "11111116"
      },
      {
        "year": "2027",
        "name": "케빈",
        "acctNo": "11111117"
      },
      {
        "year": "2027",
        "name": "제임스",
        "acctNo": "11111118"
      },
      {
        "year": "2027",
        "name": "주디",
        "acctNo": "11111119"
      },
      {
        "year": "2027",
        "name": "로이",
        "acctNo": "11111120"
      },
      {
        "year": "2027",
        "name": "에이스",
        "acctNo": "11111121"
      }
    ]
    ```
  
- 3 ) 연도별 관리점별 거래금액 합계 출력 및 거래금액 기준 내림차순 정렬(취소거래 제외)
  - Stategy
    - 연도를 입력받아 해당 연도의 관리점별 정보, 거래금액 출력 및 내림차순 정렬
    - 입력은 GET Method를 사용하며 단일년도 혹은 다수의 년도 입력 가능
  - Request
    ```
    http://localhost:8080/subject/descSumAmtBranch?years=2018,2019
    ```
  - Response
    ```
    [
      {
        "year": "2018",
        "dataList": [
          {
            "brCode": "B",
            "brName": "분당점",
            "sumAmt": 38484000
          },
          {
            "brCode": "A",
            "brName": "판교점",
            "sumAmt": 20505700
          },
          {
            "brCode": "C",
            "brName": "강남점",
            "sumAmt": 20232867
          },
          {
            "brCode": "D",
            "brName": "잠실점",
            "sumAmt": 14000000
          }
        ]
      },
      {
        "year": "2019",
        "dataList": [
          {
            "brCode": "A",
            "brName": "판교점",
            "sumAmt": 66795100
          },
          {
            "brCode": "B",
            "brName": "분당점",
            "sumAmt": 45396700
          },
          {
            "brCode": "C",
            "brName": "강남점",
            "sumAmt": 19500000
          },
          {
            "brCode": "D",
            "brName": "잠실점",
            "sumAmt": 6000000
          }
        ]
      }
    ]
    ```
  - ETC
    - 잘못된 케이스 입력 시, 오류 처리
    ```
    http://localhost:8080/subject/descSumAmtBranch?years=2038
    ```
    ```
    {
      "404": "br code not found error"
    }
    ```
- 4 ) 분당점을 판교점으로 이관 후 입력한 지점의 정보와 거래금액 출력(취소거래 제외)
  - Stategy
    - 통폐합 기능을 구현. 입력은 GET Method 사용. 계정정보 테이블에서 피합병된 관리점의 코드를 합병한 관리점의 코드로 변경  
    - 입력한 지점의 정보와 거래금액을 출력하는 기능 구현. 입력은 GET Method를 사용하며 단일 관리점명 혹은 다수의 관리점명 입력가능
  - 이관 ) Request
    ```
    http://localhost:8080/bran/merge?fromName=분당점&toName=판교점
    ```
  - 이관 ) Response
    ```
    {
      "200": "분당점 is merged to 판교점"
    }
    ```
  - 출력 ) Request
    ```
    http://localhost:8080/subject/sumAmtBranch?brNames=판교점
    ```
  - 출력 ) Response
    ```
    [
      {
        "brName": "판교점",
        "brCode": "A",
        "sumAmt": 171181500
      }
    ]
    ```
  - ETC
    - 출력 기능은 다수의 관리점명을 입력받을 수 있기 때문에 출력도 리스트로 출력
      요구사항처럼 단건의 출력을 필요 시, 수정
    - 분당점 출력 시, error 발생 요구사항에 따른 Exception 처리
    ```
    http://localhost:8080/subject/sumAmtBranch?brNames=분당점
    ```
    ```
    {
      "404": "br code not found error"
    }
    ```
