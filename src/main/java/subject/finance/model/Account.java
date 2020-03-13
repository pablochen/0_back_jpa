package subject.finance.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    private String acctNo;
    private String acctNm;
    private String brCode;

}
