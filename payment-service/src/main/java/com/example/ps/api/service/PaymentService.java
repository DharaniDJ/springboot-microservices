package com.example.ps.api.service;

import com.example.ps.api.repository.PaymentRepository;
import com.example.ps.api.entity.Payment;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return repository.save(payment);
    }
    
    public String paymentProcessing(){
        // api should be 3rd party payment gateway (like paypal may be)
        return new Random().nextBoolean() ? "success" : "false";
    }
}
