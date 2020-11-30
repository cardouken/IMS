package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transactions;
import ee.uustal.ims.entity.Wallet;
import ee.uustal.ims.exception.ApplicationLogicException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        Transactions transaction = transactionService.add(player, txId, balanceChange);
        Wallet wallet = createWallet(player, txId);

        if (wallet != null) {
            return new BalanceChangeResult()
                    .setTxId(txId)
                    .setBalanceChange(balanceChange)
                    .setBalanceVersion(wallet.getVersion())
                    .setBalance(wallet.getBalance());
        }

        wallet = createWallet(player);
        if (!wallet.apply(transaction)) {
            throw new RuntimeException("wrong version");
        }

        return new BalanceChangeResult()
                .setTxId(txId)
                .setBalanceChange(balanceChange)
                .setBalanceVersion(wallet.getVersion())
                .setBalance(wallet.getBalance());
    }

    private Wallet createWallet(Player player) {
        List<Transactions> transactions = transactionService.findAllByPlayer(player.getId());
        //        wallet.apply(transactions);
        return new Wallet(player, transactions);
    }

    private Wallet createWallet(Player player, Integer txId) {
        List<Transactions> existingTransactions = transactionService.findAllUntilId(player, txId);
        if (existingTransactions.size() == 0) {
            return null;
        }
        return new Wallet(player, existingTransactions);
//        wallet.apply(existingTransactions);
//        return wallet;
    }

//    private void initBalance(String txId, BigDecimal balanceChange) {
//        transactionRepository.add(
//                new Transaction()
//                        .setId(txId)
//                        .setBalanceChange(balanceChange)
//                        .setTimestamp(LocalDateTime.now())
//        );
//    }
}