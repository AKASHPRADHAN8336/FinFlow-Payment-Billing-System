package com.project.BillingService.service.Impl;

import com.project.BillingService.dto.BillRequest;
import com.project.BillingService.dto.UserDTO;
import com.project.BillingService.mapper.BillRequestToBill;
import com.project.BillingService.model.Bill;
import com.project.BillingService.model.BillStatus;
import com.project.BillingService.repository.BillingRepository;
import com.project.BillingService.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private WebClient webClient;
    @Autowired
    private BillingRepository billingRepository;

    @Override
    public Boolean updateStatus(Bill bill) {

        Bill billing = billingRepository.findById(bill.getId())
                .orElseThrow(() -> new RuntimeException("Bill not found"));


        bill.setStatus(BillStatus.PAID);
        billingRepository.save(bill);
        return true;

    }

    @Override
    public Bill createManualBill(BillRequest request) {


        Bill bill = BillRequestToBill.BillReqToBill(request);

        return billingRepository.save(bill);
    }

    @Override
    public List<Bill> getBillsByUser(Long userId) {

        return billingRepository.findByUserId(userId);
    }

    @Override
    public Bill getBillById(Long billId) {
        return billingRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    @Override
    public void generateMonthlyBills() {

        List<UserDTO> users = webClient
                .get()
                .uri("http://localhost:8081/users/allUsers")
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();


        if (users == null || users.isEmpty()) {
            throw new RuntimeException("No users found to generate bills");
        }

        List<Long> userIds = users.stream()
                .map(UserDTO::getId)
                .toList();



        for (long userId :userIds ) {

            Bill bill = new Bill();
            bill.setUserId(userId);
            bill.setBillType("Electricity Bill");
            bill.setAmount(generateRandomAmount());
            bill.setGeneratedDate(LocalDate.now());
            bill.setDueDate(LocalDate.now().plusDays(10));
            bill.setStatus(BillStatus.PENDING);

            billingRepository.save(bill);

            Bill bill2 = new Bill();
            bill2.setUserId(userId);
            bill2.setBillType("Phone Bill");
            bill2.setAmount(generateRandomAmount());
            bill2.setGeneratedDate(LocalDate.now());
            bill2.setDueDate(LocalDate.now().plusDays(10));
            bill2.setStatus(BillStatus.PENDING);

            billingRepository.save(bill2);


            Bill bill3 = new Bill();
            bill3.setUserId(userId);
            bill3.setBillType("Water Bill");
            bill3.setAmount(generateRandomAmount());
            bill3.setGeneratedDate(LocalDate.now());
            bill3.setDueDate(LocalDate.now().plusDays(10));
            bill3.setStatus(BillStatus.PENDING);

            billingRepository.save(bill3);

            Bill bill4 = new Bill();
            bill4.setUserId(userId);
            bill4.setBillType("Insurance Bill");
            bill4.setAmount(generateRandomAmount());
            bill4.setGeneratedDate(LocalDate.now());
            bill4.setDueDate(LocalDate.now().plusDays(10));
            bill4.setStatus(BillStatus.PENDING);

            billingRepository.save(bill4);
        }

    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void autoGenerateMonthlyBills() {
        generateMonthlyBills();
        System.out.println("Auto-Monthly Bills Generated Successfully!");
    }


    private BigDecimal generateRandomAmount() {
        int amount = new Random().nextInt(500) + 200; // Range: 200–700
        return BigDecimal.valueOf(amount);
    }
}
