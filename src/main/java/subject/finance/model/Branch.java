package subject.finance.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    private String brCode;
    private String brName;
}
