package ee.uustal.ims.repository;

import ee.uustal.ims.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    public Transaction findById(String txId) {
        throw new RuntimeException();
    }

    public Transaction add(Transaction transaction) {
        return transaction;
    }

    public List<Transaction> findAllByPlayer(String id) {
        throw new RuntimeException();
    }
}
