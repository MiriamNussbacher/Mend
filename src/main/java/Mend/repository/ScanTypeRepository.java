package Mend.repository;
import Mend.domain.ScanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScanTypeRepository extends JpaRepository<ScanType, Integer> {
    Optional<ScanType> findByScanTypeValue(String scanTypeValue);

}
