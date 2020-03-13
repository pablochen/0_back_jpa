package subject.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import subject.finance.model.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
   //4번 과제 : 계좌에 등록된 관리점 코드를 피합병 관리점 코드를 합병 관리점 코드로 변경
   @Transactional
   @Modifying
   @Query(value =
      "UPDATE ACCOUNT \n" +
         "SET BR_CODE = ?2 \n" +
      "WHERE BR_CODE = ?1 "
           , nativeQuery = true)
   void ModifyByBrCode(String mergedCode, String mergingCode);

}
