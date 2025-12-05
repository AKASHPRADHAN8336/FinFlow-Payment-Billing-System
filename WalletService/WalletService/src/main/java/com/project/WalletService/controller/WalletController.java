package com.project.WalletService.controller;


import com.project.WalletService.dto.WalletRequest;
import com.project.WalletService.dto.WalletResponse;
import com.project.WalletService.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<WalletResponse> createWallet(@PathVariable Long userId) {
        WalletResponse response = walletService.createWallet(userId);
        return new ResponseEntity<>(response , HttpStatus.CREATED);
    }

    @PostMapping("/deduct")
    public ResponseEntity<WalletResponse> deductMoney(@RequestBody WalletRequest request) {
        WalletResponse response = walletService.deductMoney(request);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<String>> getHistory(@PathVariable Long userId) {
        List<String> history = walletService.getTransactionHistory(userId);
        return new ResponseEntity<>(history,HttpStatus.OK);
    }


    @PostMapping("/add/{userId}/{amount}")
    public ResponseEntity<WalletResponse> addMoneyToWallet(@PathVariable Long userId , @PathVariable BigDecimal amount){
        WalletResponse walletResponse = walletService.addMoneyToWallet(userId,amount);

        return new ResponseEntity<>(walletResponse , HttpStatus.CREATED);
    }


    @PutMapping("/deactivate/{userId}")
    public ResponseEntity<String> deactivateWallet(@PathVariable Long userId) {
        walletService.deactivateWallet(userId);
        return new ResponseEntity<>("Deactivated wallet " + userId , HttpStatus.ACCEPTED);
    }
}
