package subject.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import subject.finance.model.Transaction;

import java.util.List;
import java.util.Map;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 1번 과제 : 거래내역에서 연도, 계좌별로 거래금액 합계 구한 후 계좌명 조인
    // DESC 사용하여 정렬후 ROWNUM <= 1로 최대 계좌만 한정
    @Query(value =
            "SELECT \n" +
                "A.YEAR, A.ACCT_NO, B.ACCT_NM, A.SUM_AMT\n" +
            "FROM\n" +
            "( \n" +
                    "SELECT\n" +
                        "LEFT(T.TRX_DATE, 4) AS YEAR, T.ACCT_NO,\n" +
                        "SUM(AMT) - SUM(FEE) AS SUM_AMT\n" +
                    "FROM TRANSACTION T\n" +
                    "WHERE LEFT(T.TRX_DATE, 4) = ?1\n" +
                        "AND T.CANCEL_YN = 'N'\n" +
                    "GROUP BY LEFT(T.TRX_DATE, 4), T.ACCT_NO\n" +
                    "ORDER BY SUM_AMT DESC\n" +
            ") A\n" +
            "LEFT JOIN ACCOUNT B\n" +
                "ON A.ACCT_NO = B.ACCT_NO\n" +
            "WHERE ROWNUM <= 1 "
    , nativeQuery = true)
    Map<String, Object> findByYearAcctSumAmt(String year);

    // 2번 과제 : 입력받은 년도 기준으로 거래정보에서 NOT IN 으로 거래가 없는 계좌로 한정
    @Query(value =
            "SELECT\n" +
                "?1 AS YEAR, A.ACCT_NO, A.ACCT_NM\n" +
            "FROM ACCOUNT A\n" +
            "WHERE A.ACCT_NO NOT IN\n" +
            "(\n" +
                "SELECT DISTINCT ACCT_NO\n" +
                "FROM TRANSACTION\n" +
                "WHERE LEFT(TRX_DATE,4) = ?1\n" +
            ")"
        , nativeQuery = true)
    List<Map<String, Object>> findAllByYearAcctNull(String year);

    // 3번 과제 : 관리점-계좌-거래내역 순으로 조인하여 관리점별 년도별 금액을 구하고 DESC로 정렬
    @Query(value =
            "SELECT \n" +
                "A.BR_CODE, A.BR_NAME, SUM(AMT) - SUM(FEE) AS SUM_AMT\n" +
            "FROM BRANCH A\n" +
            "LEFT JOIN ACCOUNT B\n" +
                "ON A.BR_CODE = B.BR_CODE\n" +
            "LEFT JOIN TRANSACTION C\n" +
                "ON B.ACCT_NO = C.ACCT_NO\n" +
            "WHERE C.CANCEL_YN = 'N'\n" +
                "AND LEFT(C.TRX_DATE,4) = ?1 \n" +
            "GROUP BY A.BR_CODE, A.BR_NAME\n" +
            "ORDER BY SUM_AMT DESC"
            , nativeQuery = true)
    List<Map<String, Object>> findAllByBranchSumAmtDesc(String year);

    // 4번 과제 : 관리점-계좌-거래내역 순으로 조인하여 관리점별 금액을 구하고 DESC로 정렬
    @Query(value =
            "SELECT \n" +
                "A.BR_CODE, A.BR_NAME, SUM(AMT) - SUM(FEE) AS SUM_AMT\n" +
            "FROM BRANCH A\n" +
            "LEFT JOIN ACCOUNT B\n" +
                "ON A.BR_CODE = B.BR_CODE\n" +
            "LEFT JOIN TRANSACTION C\n" +
                "ON B.ACCT_NO = C.ACCT_NO\n" +
            "WHERE C.CANCEL_YN = 'N'\n" +
                "AND A.BR_NAME = ?1 \n" +
            "GROUP BY A.BR_CODE, A.BR_NAME"
            , nativeQuery = true)
    Map<String, Object> findByBrNameSumAmt(String brName);


}
