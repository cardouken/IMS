package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.TransactionEntity;
import ee.uustal.ims.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final Map<Integer, TransactionEntity> storage = new ConcurrentHashMap<>();

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<TransactionEntity> findAllUntilId(Player player, Integer txId) {
        return storage.values().stream()
                .filter(t -> t.getId() <= txId)
                .collect(Collectors.toList());

//        return transactionRepository.findAllUntilId(player.getId(), txId);
    }

    @Override
    public TransactionEntity add(Player player, Integer txId, BigDecimal balanceChange) {
        final TransactionEntity transaction = new TransactionEntity()
                .setId(txId)
                .setPlayerId(player.getId())
                .setTimestamp(LocalDateTime.now())
                .setBalanceChange(balanceChange);

        BigDecimal balance = BigDecimal.ZERO;
        if (storage.size() >= 1000) {
            for (int i = storage.size(); i > storage.size() - 1000; i--) {
                balance = balance.add(storage.get(i).getBalanceChange());
            }
            flushMemoryAndSaveToDatabase(storage);
        }

        return storage.computeIfAbsent(txId, t -> transaction);
//        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionEntity> findAllByPlayer(Integer playerId) {
//        return transactionRepository.findAllById(playerId);
        return storage.values().stream()
                .filter(t -> Objects.equals(t.getPlayerId(), playerId))
                .collect(Collectors.toList());
    }

    @Override
    public void flushMemoryAndSaveToDatabase(Map<Integer, TransactionEntity> transactions) {
        transactionRepository.saveAll(storage.values());
        transactions.clear();
    }

    @Override
    public void cleanUp() {
        transactionRepository.deleteAll();
        storage.clear();
    }
}
