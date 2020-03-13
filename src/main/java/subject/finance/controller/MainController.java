package subject.finance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import subject.finance.service.MainService;

import java.io.*;
import java.util.*;


@RestController
public class MainController {
    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @Autowired
    MainService mainService;

    @RequestMapping(value = "/init/database", method = RequestMethod.GET)
    public ResponseEntity initDatabase() throws IOException {
        //공통 과제 : 메소드를 재사용하여 각각의 파일을 읽어서 저장
        Boolean brBool, acBool, trxBool;
        brBool = mainService.saveFileInfo("data/데이터_관리점정보.csv");
        acBool = mainService.saveFileInfo("data/데이터_계좌정보.csv");
        trxBool = mainService.saveFileInfo("data/데이터_거래내역.csv");

        if((brBool && acBool && trxBool) == true) // 세개의 데이터가 모두 입력되었다면 성공
            return new ResponseEntity<>(Collections.singletonMap("200", "database init success"), httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.singletonMap("404", "database init failed"), httpHeaders, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/subject/maxSumAmtAcct", method = RequestMethod.GET)
    public ResponseEntity subjectMaxSumAmtAcct(@RequestParam("years") List<String> years) throws IOException {
        //1번 과제 : 년도별 합계금액이 많은 고객 추출 메소드 : 년도 입력 (리스트 가능) ex: 2018,2019
        List<Map<String, Object>> acctMaxList = new ArrayList<Map<String, Object>>();
        for (String year : years)
            acctMaxList.add(mainService.findByYearAcctSumAmt(year));

        if(!Objects.isNull(acctMaxList.get(0).get("year"))) // 리턴받은 고객의 데이터가 존재한다면 성공
            return new ResponseEntity<>(acctMaxList, httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.singletonMap("404", "acct no not found error"), httpHeaders, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/subject/notTrxAcct", method = RequestMethod.GET)
    public ResponseEntity subjectNotTrxAcct(@RequestParam("years") List<String> years) throws IOException {
        // 2번 과제 : 년도별 거래가 없는 고객 추출 메소드 : 년도 입력 (리스트 가능) ex: 2018,2019
        List<Map<String, Object>> acctNotList = new ArrayList<Map<String, Object>>();
        for (String year : years)
            acctNotList.addAll(mainService.findAllByYearAcctNull(year));

        if(!Objects.isNull(acctNotList.get(0).get("year"))) // 리턴받은 고객의 데이터가 존재한다면 성공
            return new ResponseEntity<>(acctNotList, httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.singletonMap("404", "acct no not found error"), httpHeaders, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/subject/descSumAmtBranch", method = RequestMethod.GET)
    public ResponseEntity subjectDescSumAmtBranch(@RequestParam("years") List<String> years) throws IOException {
        // 3번 과제 : 년도별 합계금액 순 관리점 추출 메소드 : 년도 입력 (리스트 가능) ex: 2018,2019
        List<Map<String, Object>> branchList = new ArrayList<Map<String, Object>>();
        Map<String, Object> branch;
        for (String year : years) {
            branch = new LinkedHashMap<String, Object>();
            branch.put("year",year);
            branch.put("dataList",mainService.findAllByBranchSumAmtDesc(year));
            branchList.add(branch);
        }

        if(!branchList.get(0).get("dataList").toString().equals("[]")) // 리턴받은 데이터의 dataList가 비어있지 않다면 성공
            return new ResponseEntity<>(branchList, httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.singletonMap("404", "br code not found error"), httpHeaders, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/bran/merge", method = RequestMethod.GET)
    public ResponseEntity updateData(@RequestParam("fromName") String fromName, @RequestParam("toName") String toName) throws Exception {
        // 4번 과제를 위한 통폐합 메소드 : ex: fromName=분당점, toName=판교점
        String  mergedBrCode = mainService.findByBrName(fromName).getBrCode();
        String  mergingBrCode = mainService.findByBrName(toName).getBrCode();
        mainService.modifyByBrCode(mergedBrCode, mergingBrCode);
        return new ResponseEntity<>(Collections.singletonMap("200", fromName + " is merged to " + toName), httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/subject/sumAmtBranch", method = RequestMethod.GET)
    public ResponseEntity subjectSumAmtBranch(@RequestParam("brNames") List<String> brNames) throws IOException {
        // 4번 과제 : 관리점명을 입력받아 거래 금액 합계를 리턴 : 관리점명 입력 (리스트 가능) ex: 판교점,분당점
        // 요구사항처럼 하나의 관리점의 정보만 필요하다면 수정 > as-is : List형태
        List<Map<String, Object>> branchList = new ArrayList<Map<String, Object>>();
        Map<String, Object> branch;
        for (String brName : brNames) {
            branch = mainService.findByBrNameSumAmt(brName);
            if(!Objects.isNull(branch.get("brCode"))) branchList.add(branch);
        }

        if(!branchList.toString().equals("[]")) // 리턴받은 데이터의 dataList가 비어있지 않다면 성공
            return new ResponseEntity<>(branchList, httpHeaders, HttpStatus.OK);
        else
            return new ResponseEntity<>(Collections.singletonMap("404", "br code not found error"), httpHeaders, HttpStatus.NOT_FOUND);
    }
}
