package ee.uustal.ims.repository;

import ee.uustal.ims.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Integer> {

    List<Transactions> findAllById(Integer id);

    @Query("SELECT t FROM Transactions t WHERE t.playerId = ?1 AND t.id < ?2")
    List<Transactions> findAllUntilId(Integer playerId, Integer id);
}
