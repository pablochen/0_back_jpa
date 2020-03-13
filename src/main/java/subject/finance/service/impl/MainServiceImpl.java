package subject.finance.service.impl;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subject.finance.model.Account;
import subject.finance.model.AccountTransactionId;
import subject.finance.model.Branch;
import subject.finance.model.Transaction;
import subject.finance.repository.AccountRepository;
import subject.finance.repository.BranchRepository;
import subject.finance.repository.TransactionRepository;
import subject.finance.service.MainService;

import java.io.*;
import java.util.*;

@Service
public class MainServiceImpl implements MainService {
    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveBranchInfo(String brCode, String brName) {
        Branch branch = new Branch(brCode, brName);
        branchRepository.save(branch);
    }

    @Override
    public void saveAccountInfo(String acctNo, String acctNm, String brCode) {
        Account account = new Account(acctNo, acctNm, brCode);
        accountRepository.save(account);
    }

    @Override
    public void saveTransactionInfo(AccountTransactionId id, int amt, int fee, String cancelYn) {
        Transaction transaction = new Transaction(id, amt, fee, cancelYn);
        transactionRepository.save(transaction);
    }
    @Override
    public Boolean saveFileInfo(String filePath) throws IOException {
        // RUNNABLE JAR 로 할 시, 파일을 읽어오지 못하기 때문에 인풋스트림으로 처리
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        // JAR 실행 시, 인코딩 지정이 안되있기 때문에 UTF-8로 지정하여 깨짐 방지
        Reader reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream(),"UTF-8"));
        CSVReader csvReader = new CSVReader(reader);

        String fname = classPathResource.getFilename();
        String[] line;
        csvReader.readNext();

        // 파일명에 따라 각각의 REPO 호출하여 데이터 저장
        while ((line = csvReader.readNext()) != null) {
            switch (fname) {
                case "데이터_관리점정보.csv":
                    saveBranchInfo(line[0], line[1]);
                    break;

                case "데이터_계좌정보.csv":
                    saveAccountInfo(line[0], line[1], line[2]);
                    break;

                case "데이터_거래내역.csv":
                    // 복합키를 가진 거래내역은 PK를 생성한 후 저장
                    AccountTransactionId newId = new AccountTransactionId();
                    newId.setTrxDate(line[0]);
                    newId.setAcctNo(line[1]);
                    newId.setTrxNo(Integer.parseInt(line[2]));
                    saveTransactionInfo(newId, Integer.parseInt(line[3]),Integer.parseInt(line[4]), line[5]);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override
    public Map<String, Object> findByYearAcctSumAmt(String year) {
        // 1번 과제 : 메소드 호출 후, json 네이밍 하여 리턴
        Map<String, Object> ret = new LinkedHashMap<>();
        Map<String, Object> res = transactionRepository.findByYearAcctSumAmt(year);
        ret.put("year",res.get("YEAR"));
        ret.put("name",res.get("ACCT_NM"));
        ret.put("acctNo",res.get("ACCT_NO"));
        ret.put("sumAmt",res.get("SUM_AMT"));
        return ret;
    }

    @Override
    public List<Map<String, Object>> findAllByYearAcctNull(String year) {
        // 2번 과제 : 메소드 호출 후, 객체 하나씩 json 네이밍 하여 리턴
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> res = transactionRepository.findAllByYearAcctNull(year);
        for (Map<String, Object> acc : res) {
            Map<String, Object> temp = new LinkedHashMap<String,Object>();
            temp.put("year", acc.get("YEAR"));
            temp.put("name", acc.get("ACCT_NM"));
            temp.put("acctNo", acc.get("ACCT_NO"));
            ret.add(temp);
        }
        return ret;
    }

    @Override
    public List<Map<String, Object>> findAllByBranchSumAmtDesc(String year) {
        // 3번 과제 : 메소드 호출 후, 객체 하나씩 json 네이밍 하여 리턴
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> res = transactionRepository.findAllByBranchSumAmtDesc(year);
        for (Map<String, Object> acc : res) {
            Map<String, Object> temp = new LinkedHashMap<String,Object>();
            temp.put("brCode", acc.get("BR_CODE"));
            temp.put("brName", acc.get("BR_NAME"));
            temp.put("sumAmt", acc.get("SUM_AMT"));
            ret.add(temp);
        }
        return ret;
    }

    @Override
    public Map<String, Object> findByBrNameSumAmt(String brName) {
        // 4번 과제 - 1 : 지점별 금액 호출 메소드, json 네이밍하여 리턴
        Map<String, Object> ret = new LinkedHashMap<>();
        Map<String, Object> res = transactionRepository.findByBrNameSumAmt(brName);
        ret.put("brName",res.get("BR_NAME"));
        ret.put("brCode",res.get("BR_CODE"));
        ret.put("sumAmt",res.get("SUM_AMT"));
        return ret;
    }

    @Override
    public Branch findByBrName(String brName) {
        // 4번 과제 - 2 : 관리점 명으로 조회해서 관리점 코드를 가져오기 위한 메소드
        Branch branch = branchRepository.findByBrName(brName);
        return branch;
    }

    @Override
    @Transactional
    public void modifyByBrCode(String mergedCode, String mergingCode){
        // 4번 과제 - 3 : 4-2를 활용하여 관리점 명 > 관리점 코드 > 계좌정보의 관리점을 수정 하기 위한 메소드
        accountRepository.ModifyByBrCode(mergedCode, mergingCode);
    }

}
