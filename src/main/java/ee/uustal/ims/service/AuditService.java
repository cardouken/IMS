package ee.uustal.ims.service;

import ee.uustal.ims.persistence.entity.Player;
import ee.uustal.ims.persistence.entity.Transaction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class AuditService {

    private final Logger logger = LogManager.getLogger(getClass());

    public void logTransactionIn(Transaction transaction, Player player) {
        logger.info(
                MessageFormat.format(
                        "IN: transaction ID: {0} by username: {1}, balance change: {2}",
                        transaction.getId(),
                        player.getUsername(),
                        String.valueOf(transaction.getBalanceChange())
                )
        );
    }

    public void logTransactionOut(Transaction transaction, Player player) {
        logger.info(
                MessageFormat.format(
                        "OUT: transaction ID: {0}, balance version: {1}, balance change: {2}, player balance after change: {3}",
                        transaction.getId(),
                        transaction.getBalanceVersion(),
                        String.valueOf(transaction.getBalanceChange()),
                        player.getBalance().toString()
                )
        );
    }
}
