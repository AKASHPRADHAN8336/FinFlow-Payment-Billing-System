package com.project.WalletService.service.Impl;

import com.project.WalletService.dto.WalletRequest;
import com.project.WalletService.dto.WalletResponse;
import com.project.WalletService.mapper.WalletToWalletRes;
import com.project.WalletService.model.TransactionType;
import com.project.WalletService.model.Wallet;
import com.project.WalletService.model.WalletStatus;
import com.project.WalletService.model.WalletTransaction;
import com.project.WalletService.repository.TransactionRepository;
import com.project.WalletService.repository.WalletRepository;
import com.project.WalletService.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public WalletResponse createWallet(Long userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setStatus(WalletStatus.ACTIVE);

        Wallet savedWallet=walletRepository.save(wallet);



        saveTransaction(wallet, BigDecimal.ZERO, TransactionType.ADD);

        return WalletToWalletRes.WalletToWalletResponse(savedWallet);
    }

    @Override
    public WalletResponse deductMoney(WalletRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getStatus() == WalletStatus.INACTIVE) {
            throw new RuntimeException("Wallet is deactivated");
        }

        if (wallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(request.getAmount()));
        Wallet deductWallet=walletRepository.save(wallet);

        saveTransaction(wallet, request.getAmount(), TransactionType.DEDUCT);

        return WalletToWalletRes.WalletToWalletResponse(deductWallet);
    }

    @Override
    public List<String> getTransactionHistory(Long userId) {
        return transactionRepository.findByUserId(userId)
                .stream()
                .map(tx -> tx.getCreatedAt()+ ":"+ tx.getType() + " : " + tx.getAmount())
                .collect(Collectors.toList());
    }

    @Override
    public String deactivateWallet(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setStatus(WalletStatus.INACTIVE);
        walletRepository.save(wallet);

        saveTransaction(wallet, BigDecimal.ZERO, TransactionType.DEACTIVATED);

        return new String("Wallet Deactivated");

    }

    @Override
    public WalletResponse addMoneyToWallet(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                        .orElseThrow(() -> new RuntimeException("wallet not found"));

        if (wallet.getStatus() == WalletStatus.INACTIVE) {
            throw new RuntimeException("Wallet is deactivated");
        }

        wallet.setBalance(wallet.getBalance().add(amount));
        saveTransaction(wallet, amount, TransactionType.ADD);

        Wallet savedWallet=walletRepository.save(wallet);

        return WalletToWalletRes.WalletToWalletResponse(savedWallet);
    }

    private void saveTransaction(Wallet wallet, BigDecimal amount, TransactionType type) {

        WalletTransaction walletTransaction = new WalletTransaction();
        walletTransaction.setWalletId(wallet.getId());
        walletTransaction.setUserId(wallet.getUserId());
        walletTransaction.setAmount(amount);
        walletTransaction.setType(type);

        transactionRepository.save(walletTransaction);
    }


}
