package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transaction;
import ee.uustal.ims.entity.Wallet;
import ee.uustal.ims.exception.ApplicationLogicException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Value("${ims.max-balance-increase}")
    private long MAX_BALANCE_INCREASE;

    private final PlayerService playerService;
    private final TransactionService transactionService;

    public WalletServiceImpl(PlayerService playerService, TransactionService transactionService) {
        this.playerService = playerService;
        this.transactionService = transactionService;
    }

    @Override
    public BalanceChangeResult updateBalance(String username, Integer txId, BigDecimal balanceChange) {
        if (balanceChange.longValue() >= MAX_BALANCE_INCREASE) {
            throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_CHANGE_EXCEEDS_MAX_LIMIT);
        }

        final Player player = playerService.getOrCreatePlayer(username);

        synchronized (player) {
            final Wallet wallet = createWallet(player, txId);
            final Transaction transaction = new Transaction()
                    .setId(txId)
                    .setPlayerId(player.getId())
                    .setTimestamp(LocalDateTime.now())
                    .setBalanceChange(balanceChange);
            wallet.apply(transaction);
            if (wallet.getBalance().signum() == -1) {
                throw new ApplicationLogicException(ApplicationLogicException.ErrorCode.BALANCE_LESS_THAN_ZERO);
            }
            transactionService.add(transaction);
            player.setBalance(wallet.getBalance());
            player.setBalanceVersion(wallet.getVersion());
            return new BalanceChangeResult()
                    .setTxId(txId)
                    .setBalanceChange(balanceChange)
                    .setBalanceVersion(wallet.getVersion())
                    .setBalance(wallet.getBalance());
        }
    }

    @Override
    public void cleanUp() {
        transactionService.cleanUp();
    }

    private Wallet createWallet(Player player, Integer txId) {
        List<Transaction> existingTransactions = transactionService.findAllUntilId(player, player.getBalanceVersion(), txId);
        if (existingTransactions.size() == 0) {
            List<Transaction> transactions = transactionService.findAllByPlayer(player.getId(), player.getBalanceVersion());
            return new Wallet(player, transactions);
        } else {
            return new Wallet(player, existingTransactions);
        }
    }

}