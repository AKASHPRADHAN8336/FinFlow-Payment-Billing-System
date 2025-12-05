package com.project.WalletService.service;

import com.project.WalletService.dto.WalletRequest;
import com.project.WalletService.dto.WalletResponse;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    WalletResponse createWallet(Long userId);

    WalletResponse deductMoney(WalletRequest request);

    List<String> getTransactionHistory(Long userId);

    String deactivateWallet(Long userId);

    WalletResponse addMoneyToWallet(Long userId, BigDecimal amount);
}
