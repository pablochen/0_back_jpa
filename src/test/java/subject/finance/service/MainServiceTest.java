package subject.finance.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import subject.finance.service.impl.MainServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
class MainControllerTest extends MainServiceImpl {
    @Autowired
    private MainServiceImpl mainServiceImpl;

    @BeforeEach
    void initDatabase() throws IOException {
        mainServiceImpl.saveFileInfo("data/데이터_관리점정보.csv");
        mainServiceImpl.saveFileInfo("data/데이터_계좌정보.csv");
        mainServiceImpl.saveFileInfo("data/데이터_거래내역.csv");
    }

    @Test
    void subjectMaxSumAmtAcct() throws Exception {
        // 1번 과제 : 2018년 가장 거래가 많은 사람의 계좌번호는 11111114
        Map<String, Object> ret = mainServiceImpl.findByYearAcctSumAmt("2018");
        Assertions.assertThat(ret.get("acctNo").equals(11111114));
    }

    @Test
    void subjectNotTrxAcct() throws Exception {
        // 2번 과제 : 2019년 거래가없는 첫번째 고객의 이름은 테드
        List<Map<String, Object>> ret = mainServiceImpl.findAllByYearAcctNull("2019");
        Assertions.assertThat(ret.get(0).get("name").equals("테드"));
    }

    @Test
    void subjectDescSumAmtBranch() throws Exception {
        // 3번 과제 : 2020년 가장 거래가 많은 관리점의 이름은 을지로점
        List<Map<String, Object>> ret = mainServiceImpl.findAllByBranchSumAmtDesc("2020");
        Assertions.assertThat(ret.get(0).get("brName").equals("을지로점"));
    }

    @Test
    void subjectSumAmtBranch() throws Exception {
        // 4번 과제 : 분당점의 거래금액 총합은 83880700원
        Map<String, Object> ret = mainServiceImpl.findByBrNameSumAmt("분당점");
        Assertions.assertThat(ret.get("sumAmt").equals(83880700));
    }
}