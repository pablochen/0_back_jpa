package subject.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import subject.finance.model.Branch;


@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findByBrName(String BrName);
}
