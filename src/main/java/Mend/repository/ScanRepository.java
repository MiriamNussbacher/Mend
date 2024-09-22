package Mend.repository;
import Mend.domain.Scan;
import Mend.dto.CommitScanInfoDTO;
import Mend.types.ScanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Integer> {
    long countByStatus(ScanStatus status);

    @Query("SELECT SUM(s.issues) FROM Scan s WHERE s.initiatedByUser.userId = :userId")
    Integer sumIssuesByUserId(@Param("userId") int userId);

    @Query("SELECT SUM(s.issues) FROM Scan s")
    Integer sumIssuesForAllUsers();

////
//@Query("SELECT s FROM Scan s JOIN FETCH s.commit c JOIN FETCH s.initiatedByUser u WHERE c.commitId = :commitId")
//    List<Scan> findScansByCommitId(@Param("commitId") int commitId);

//    @Query("SELECT new Mend.dto.CommitScanInfoDTO(u.userId, CONCAT(u.firstName, ' ', u.lastName), c.commitId, s.scanType.scanTypeValue, s.status, s.issues) " +
//            "FROM Scan s " +
//            "JOIN s.initiatedByUser u " +
//            "JOIN s.commit c " +
//            "WHERE c.commitId = :commitId")
//    List<CommitScanInfoDTO> findScansByCommitId1(@Param("commitId") int commitId);

}
