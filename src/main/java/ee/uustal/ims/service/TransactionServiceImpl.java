package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final Map<Integer, Transaction> storage;

    public TransactionServiceImpl() {
        this.storage = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Transaction> eldest) {
                return size() > 1000;
            }
        };
    }

    @Override
    public List<Transaction> findAllUntilId(Player player, long version, Integer transactionId) {
        return storage.values().stream()
                .filter(t -> Objects.equals(t.getPlayerId(), player.getId()))
                .filter(t -> t.getId() <= transactionId)
                .filter(t -> t.getBalanceVersion() > version)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Transaction transaction) {
        storage.putIfAbsent(transaction.getId(), transaction);
    }

    @Override
    public List<Transaction> findAllByPlayer(Integer playerId, long version) {
        return storage.values().stream()
                .filter(t -> Objects.equals(t.getPlayerId(), playerId))
                .filter(t -> t.getBalanceVersion() > version)
                .collect(Collectors.toList());
    }

    @Override
    public void cleanUp() {
        storage.clear();
    }
}
