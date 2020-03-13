package subject.finance.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AccountTransactionId implements Serializable {
    private static final long serialVersionUID = 1L;
    private String trxDate;
    private String acctNo;
    private int trxNo;
}
