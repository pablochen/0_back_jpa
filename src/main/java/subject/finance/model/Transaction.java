package subject.finance.model;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @EmbeddedId
    private AccountTransactionId accountTransactionId;

    private int amt;
    private int fee;
    private String cancelYn;
}
