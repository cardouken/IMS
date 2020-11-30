package ee.uustal.ims.service;

import ee.uustal.ims.entity.Player;
import ee.uustal.ims.entity.Transaction;
import ee.uustal.ims.entity.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final PlayerService playerService;
    private final TransactionService transactionService;

    public WalletServiceImpl(PlayerService playerService, TransactionService transactionService) {
        this.playerService = playerService;
        this.transactionService = transactionService;
    }

    @Override
    public BalanceChangeResult updateBalance(String username, String txId, BigDecimal balanceChange) {
        final Player player = playerService.getOrCreatePlayer(username);
        Wallet wallet = createWallet(player, txId);
        if (wallet != null) {
            return new BalanceChangeResult()
                    .setTxId(txId)
                    .setBalanceChange(balanceChange)
                    .setBalanceVersion(wallet.getVersion())
                    .setBalance(wallet.getBalance());
        }

        wallet = createWallet(player);
        Transaction transaction = transactionService.add(player, txId, balanceChange);
        if (!wallet.apply(transaction)) {
            throw new RuntimeException("wrong version");
        }

//        Integer errorCode = null;

        return new BalanceChangeResult()
                .setTxId(txId)
                .setBalanceChange(balanceChange)
//                .setErrorCode(errorCode)
                .setBalanceVersion(wallet.getVersion())
                .setBalance(wallet.getBalance());
    }

    private Wallet createWallet(Player player) {
        List<Transaction> transactions = transactionService.findAllByPlayer(player.getId());
        //        wallet.apply(transactions);
        return new Wallet(player, transactions);
    }

    private Wallet createWallet(Player player, String txId) {
        List<Transaction> existingTransactions = transactionService.findAllUntilId(player, txId);
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