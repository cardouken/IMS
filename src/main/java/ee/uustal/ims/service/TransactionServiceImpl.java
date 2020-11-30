package ee.uustal.ims.service;

import ee.uustal.ims.repository.TransactionRepository;
import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transactions;
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
    public List<Transactions> findAllUntilId(Player player, Integer id) {
        return transactionRepository.findAllUntilId(player.getId(), id);
    }

    @Override
    public Transactions add(Player player, Integer txId, BigDecimal balanceChange) {
        final Transactions transaction = new Transactions()
                .setId(txId)
                .setPlayerId(player.getId())
                .setTimestamp(LocalDateTime.now())
                .setBalanceChange(balanceChange);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transactions> findAllByPlayer(Integer playerId) {
        return transactionRepository.findAllById(playerId);
    }
}
