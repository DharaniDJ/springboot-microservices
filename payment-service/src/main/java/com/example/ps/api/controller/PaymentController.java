package com.example.ps.api.controller;

import com.example.ps.api.entity.Payment;
import com.example.ps.api.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    public Payment doPayment(Payment payment) {
        return service.doPayment(payment);
    }
}
