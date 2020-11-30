package ee.uustal.ims.controller;

import ee.uustal.ims.api.request.BalanceChangeRequest;
import ee.uustal.ims.service.WalletService;
import ee.uustal.ims.service.WalletServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WalletController {

    private final WalletServiceImpl walletService;

    public WalletController(WalletServiceImpl walletService) {
        this.walletService = walletService;
    }

    @PostMapping(value = "/balance/update")
    public WalletService.BalanceChangeResult updateBalance(@Valid @RequestBody BalanceChangeRequest request) {
        return walletService.updateBalance(request.getUsername(), request.getTransactionId(), request.getBalanceChange());
    }
}
