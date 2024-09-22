package Mend.repository;

import Mend.domain.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommitRepository extends JpaRepository<Commit, Integer> {


}
