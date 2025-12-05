package com.project.BillingService.controller;

import com.project.BillingService.dto.BillRequest;
import com.project.BillingService.model.Bill;
import com.project.BillingService.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;


    @PostMapping("/manualBilling")
    public ResponseEntity<Bill> createManualBill(@RequestBody BillRequest request) {
        Bill bill = billingService.createManualBill(request);
        return new ResponseEntity<>(bill , HttpStatus.CREATED);
    }


    @GetMapping("/userBills/{userId}")
    public ResponseEntity<List<Bill>> getBillsByUser(@PathVariable Long userId) {
        List<Bill> bills = billingService.getBillsByUser(userId);
        return new ResponseEntity<>(bills , HttpStatus.OK);
    }


    @GetMapping("/{billId}")
    public ResponseEntity<Bill> getBillById(@PathVariable Long billId) {
        Bill bill = billingService.getBillById(billId);
        return ResponseEntity.ok(bill);
    }


    @PostMapping("/generate/manual")
    public ResponseEntity<String> manualGenerateBills() {
        billingService.generateMonthlyBills();

        return new ResponseEntity<>("Monthly bills generated successfully ." , HttpStatus.OK);

    }

    @GetMapping("/auto/status")
    public ResponseEntity<String> autoStatus() {

        return new ResponseEntity<>("Auto-generation runs every 1st of the month at 12:00 AM." , HttpStatus.OK);

    }

    @PutMapping("/updateStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestBody Bill bill){

        Boolean status = billingService.updateStatus(bill);
        return new ResponseEntity<>(status , HttpStatus.OK);
    }




}
