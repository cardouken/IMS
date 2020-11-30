package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    Transaction findById(String id);

    List<Transaction> findAllUntilId(Player player, String id);

    Transaction add(Player player, String txId, BigDecimal balanceChange);

    List<Transaction> findAllByPlayer(String playerId);

}
