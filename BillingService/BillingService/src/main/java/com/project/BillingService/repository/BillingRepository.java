package com.project.BillingService.repository;

import com.project.BillingService.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillingRepository extends JpaRepository<Bill , Long> {

    List<Bill> findByUserId(Long userId);
}
