package subject.finance.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import subject.finance.service.impl.MainServiceImpl;
import subject.finance.vo.dbO;

import java.util.ArrayList;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MainServiceImpl mainServiceImpl;

    @BeforeEach
    void initDatabase(){
        ResponseEntity response = testRestTemplate.getForEntity("/init/database", dbO.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void subjectMaxSumAmtAcct() {
        ResponseEntity<ArrayList> response = testRestTemplate.getForEntity("/subject/maxSumAmtAcct?years=2018,2019", ArrayList.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void subjectNotTrxAcct() {
        ResponseEntity<ArrayList> response = testRestTemplate.getForEntity("/subject/notTrxAcct?years=2019,2020", ArrayList.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void subjectDescSumAmtBranch() {
        ResponseEntity<ArrayList> response = testRestTemplate.getForEntity("/subject/descSumAmtBranch?years=2018,2020", ArrayList.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }

    @Test
    void subjectSumAmtBranch() {
        ResponseEntity<ArrayList> response = testRestTemplate.getForEntity("/subject/sumAmtBranch?brNames=분당점,판교점", ArrayList.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
    }
}