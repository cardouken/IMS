package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${ims.config.max-amount-of-latest-transactions}")
    private int maxAmountOfLatestTransactions;

    private final Map<Long, Transaction> storage;

    public TransactionServiceImpl() {
        this.storage = Collections.synchronizedMap(new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, Transaction> eldest) {
                return size() > maxAmountOfLatestTransactions;
            }
        });
    }

    @Override
    public Optional<Transaction> findById(Player player, long version, long transactionId) {
        return storage.values().stream()
                .filter(t -> Objects.equals(t.getPlayerUsername(), player.getUsername()))
                .filter(t -> t.getId() == transactionId)
                .findFirst();
    }

    @Override
    public List<Transaction> findAllByPlayer(String username, long version) {
        return storage.values().stream()
                .filter(t -> Objects.equals(t.getPlayerUsername(), username))
                .filter(t -> t.getBalanceVersion() > version)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Transaction transaction) {
        storage.putIfAbsent(transaction.getId(), transaction);
    }
}
