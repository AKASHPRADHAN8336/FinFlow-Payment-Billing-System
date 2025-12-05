package com.project.BillingService.service;

import com.project.BillingService.dto.BillRequest;
import com.project.BillingService.model.Bill;

import java.util.List;

public interface BillingService {

    Boolean updateStatus(Bill bill);

    Bill createManualBill(BillRequest request);


    List<Bill> getBillsByUser(Long userId);


    Bill getBillById(Long billId);


    void generateMonthlyBills();
}
