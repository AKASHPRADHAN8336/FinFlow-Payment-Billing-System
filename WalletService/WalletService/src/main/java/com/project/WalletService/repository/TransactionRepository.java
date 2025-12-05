package com.project.WalletService.repository;

import com.project.WalletService.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<WalletTransaction , Long> {

    List<WalletTransaction> findByUserId(Long userId);
}
