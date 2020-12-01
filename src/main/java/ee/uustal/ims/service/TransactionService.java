package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.TransactionEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {

    List<TransactionEntity> findAllUntilId(Player player, Integer id);

    TransactionEntity add(Player player, Integer txId, BigDecimal balanceChange);

    List<TransactionEntity> findAllByPlayer(Integer playerId);

    void flushMemoryAndSaveToDatabase(Map<Integer, TransactionEntity> transactions);

    void cleanUp();
}
