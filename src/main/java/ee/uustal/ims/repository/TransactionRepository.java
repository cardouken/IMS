package ee.uustal.ims.repository;

import ee.uustal.ims.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

    List<TransactionEntity> findAllById(Integer id);

    @Query("SELECT t FROM TransactionEntity t WHERE t.playerId = ?1 AND t.id < ?2")
    List<TransactionEntity> findAllUntilId(Integer playerId, Integer id);

}
