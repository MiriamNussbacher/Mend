package Mend.repository;

import Mend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.userId, CONCAT(u.firstName, ' ', u.lastName) AS fullName, COUNT(s) AS scanCount " +
            "FROM Scan s " +
            "JOIN s.initiatedByUser u " +
            "GROUP BY u.firstName, u.lastName, u.userId " +
            "ORDER BY scanCount DESC")
    List<Object[]> findTopActiveUsers(Pageable pageable);


}