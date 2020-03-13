package subject.finance.service;

import subject.finance.model.AccountTransactionId;
import subject.finance.model.Branch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MainService {

    void saveBranchInfo(String brCode, String brName);
    void saveAccountInfo(String acctNo, String acctNm, String brCode);
    void saveTransactionInfo(AccountTransactionId id, int amt, int fee, String cancelYn);
    Boolean saveFileInfo(String filePath) throws IOException;

    Map<String, Object> findByYearAcctSumAmt(String year);
    List<Map<String, Object>> findAllByYearAcctNull(String year);
    List<Map<String, Object>> findAllByBranchSumAmtDesc(String year);
    Map<String, Object> findByBrNameSumAmt(String brName);
    Branch findByBrName(String brName);
    void modifyByBrCode(String mergedCode, String mergingCode);
}
