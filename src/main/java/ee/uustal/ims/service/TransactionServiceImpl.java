package ee.uustal.ims.service;

import ee.uustal.ims.repository.TransactionRepository;
import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction findById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllUntilId(Player player, String id) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Transaction add(Player player, String txId, BigDecimal balanceChange) {
        final Transaction transaction = new Transaction()
                .setId(txId)
                .setPlayerId(player.getId())
                .setTimestamp(LocalDateTime.now())
                .setBalanceChange(balanceChange);
        transactionRepository.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAllByPlayer(String playerId) {
        return transactionRepository.findAllByPlayer(playerId);
    }
}
