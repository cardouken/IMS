package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transaction;

import java.util.List;

public interface TransactionService {

    void add(Transaction transaction);

    List<Transaction> findAllUntilId(Player player, long version, Integer id);

    List<Transaction> findAllByPlayer(Integer playerId, long version);

    void cleanUp();
}
