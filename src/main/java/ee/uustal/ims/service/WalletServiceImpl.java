package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;
import ee.uustal.ims.persistence.entity.Wallet;
import ee.uustal.ims.exception.ApplicationLogicException;
import ee.uustal.ims.persistence.repository.BlacklistRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Value("${ims.config.max-balance-increase}")
    private long MAX_BALANCE_INCREASE;

    private final PlayerService playerService;
    private final TransactionService transactionService;
    private final BlacklistRepository blacklistRepository;

    public WalletServiceImpl(PlayerService playerService,
                             TransactionService transactionService,
                             BlacklistRepository blacklistRepository) {
        this.playerService = playerService;
        this.transactionService = transactionService;
        this.blacklistRepository = blacklistRepository;
    }

    @Override
    public BalanceChangeResult updateBalance(String username, Integer transactionId, BigDecimal balanceChange) {
        if (balanceChange.longValue() >= MAX_BALANCE_INCREASE) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_CHANGE_EXCEEDS_MAX_LIMIT);
        }

        final Player player = playerService.getOrCreatePlayer(username);
        if (blacklistRepository.findById(player.getId()) != null) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.PLAYER_BLACKLISTED);
        }

        synchronized (player) {
            final Wallet wallet = createWallet(player, transactionId);
            final Transaction transaction = new Transaction()
                    .setId(transactionId)
                    .setPlayerId(player.getId())
                    .setTimestamp(LocalDateTime.now())
                    .setBalanceChange(balanceChange);
            wallet.apply(transaction);

            if (wallet.balanceNegative()) {
                throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_LESS_THAN_ZERO);
            }
            transactionService.add(transaction);
            player.setBalance(wallet.getBalance());
            player.setBalanceVersion(wallet.getVersion());
            return new BalanceChangeResult()
                    .setTransactionId(transactionId)
                    .setBalanceChange(balanceChange)
                    .setBalanceVersion(wallet.getVersion())
                    .setBalance(wallet.getBalance());
        }
    }

    @Override
    public void cleanUp() {
        transactionService.cleanUp();
    }

    private Wallet createWallet(Player player, Integer transactionId) {
        List<Transaction> existingTransactions = transactionService.findAllUntilId(player, player.getBalanceVersion(), transactionId);
        if (existingTransactions.size() == 0) {
            List<Transaction> transactions = transactionService.findAllByPlayer(player.getId(), player.getBalanceVersion());
            return new Wallet(player, transactions);
        } else {
            return new Wallet(player, existingTransactions);
        }
    }

}