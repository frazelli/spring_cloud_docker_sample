package com.bankofaffiliates.transactionservice.controller;

import com.bankofaffiliates.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("abc")
    public String test() {
        return transactionService.testServerScope();
    }

}
