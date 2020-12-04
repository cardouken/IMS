package ee.uustal.ims.service;

import ee.uustal.ims.exception.ApplicationLogicException;
import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;
import ee.uustal.ims.persistence.entity.Wallet;
import ee.uustal.ims.persistence.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

    @Value("${ims.config.max-balance-increase}")
    private long MAX_BALANCE_INCREASE;

    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final BlacklistRepository blacklistRepository;
    private final LockService lockService;
    private final AuditService auditService;

    public WalletServiceImpl(PlayerService playerService,
                             TransactionService transactionService,
                             BlacklistRepository blacklistRepository,
                             LockService lockService,
                             AuditService auditService) {
        this.playerService = playerService;
        this.transactionService = transactionService;
        this.blacklistRepository = blacklistRepository;
        this.lockService = lockService;
        this.auditService = auditService;
    }

    @Override
    public BalanceChangeResult updateBalance(String username, long transactionId, BigDecimal balanceChange) {
        if (balanceChange.longValue() >= MAX_BALANCE_INCREASE) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_CHANGE_EXCEEDS_MAX_LIMIT);
        }

        final Player player = playerService.getOrCreate(username);
        if (blacklistRepository.findByUsername(player.getUsername()) != null) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.PLAYER_BLACKLISTED);
        }

        lockService.lock(player.getUsername());
        try {
            Wallet wallet;
            Optional<Transaction> existingTransactions = transactionService.findById(player, player.getBalanceVersion(), transactionId);

            if (existingTransactions.isPresent()) {
                final Transaction transaction = existingTransactions.stream()
                        .filter(t -> Objects.equals(transactionId, t.getId()))
                        .findFirst()
                        .orElseThrow(EntityNotFoundException::new);

                return new BalanceChangeResult()
                        .setTransactionId(transactionId)
                        .setBalanceChange(transaction.getBalanceChange())
                        .setBalanceVersion(transaction.getBalanceVersion())
                        .setBalance(transaction.getBalance().add(transaction.getBalanceChange()));
            }
            List<Transaction> transactions = transactionService.findAllByPlayer(player.getUsername(), player.getBalanceVersion());
            wallet = new Wallet(player, transactions);

            final Transaction transaction = new Transaction()
                    .setId(transactionId)
                    .setPlayerUsername(player.getUsername())
                    .setTimestamp(LocalDateTime.now())
                    .setBalanceChange(balanceChange)
                    .setBalance(wallet.getBalance());
            wallet.apply(transaction);

            auditService.log(transaction, player);
            transactionService.add(transaction);
            player.setBalance(wallet.getBalance());
            player.setBalanceVersion(wallet.getVersion());

            auditService.logTransaction(transaction, player);

            return new BalanceChangeResult()
                    .setTransactionId(transactionId)
                    .setBalanceChange(balanceChange)
                    .setBalanceVersion(wallet.getVersion())
                    .setBalance(wallet.getBalance());
        } finally {
            lockService.unlock(player.getUsername());
        }
    }

}