package com.project.BillingService.mapper;

import com.project.BillingService.dto.BillRequest;
import com.project.BillingService.model.Bill;
import com.project.BillingService.model.BillStatus;

import java.time.LocalDate;

public class BillRequestToBill {

    public static Bill BillReqToBill(BillRequest request){
        Bill bill = new Bill();
        bill.setUserId(request.getUserId());
        bill.setBillType(request.getBillType());
        bill.setAmount(request.getAmount());
        bill.setGeneratedDate(LocalDate.now());
        bill.setDueDate(LocalDate.now().plusDays(10));
        bill.setStatus(BillStatus.PENDING);



        return bill;
    }
}
