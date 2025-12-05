package com.project.WalletService.mapper;

import com.project.WalletService.dto.WalletResponse;
import com.project.WalletService.model.Wallet;

public class WalletToWalletRes {

    public static WalletResponse WalletToWalletResponse(Wallet wallet){

        WalletResponse walletResponse = new WalletResponse(
                wallet.getId(),
                wallet.getBalance(),
                wallet.getStatus(),
                wallet.getUpdatedAt()
        );
         return walletResponse;


    }
}
