package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;

import java.util.Optional;

public interface TransactionService {

    void add(Transaction transaction);

    Optional<Transaction> findById(Player player, long id);

}
