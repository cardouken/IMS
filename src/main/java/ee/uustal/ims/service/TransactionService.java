package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transactions;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    List<Transactions> findAllUntilId(Player player, Integer id);

    Transactions add(Player player, Integer txId, BigDecimal balanceChange);

    List<Transactions> findAllByPlayer(Integer playerId);

}
