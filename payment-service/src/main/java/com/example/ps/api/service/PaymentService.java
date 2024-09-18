package com.example.ps.api.service;

import com.example.ps.api.repository.PaymentRepository;
import com.example.ps.api.entity.Payment;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(UUID.randomUUID().toString());
        return repository.save(payment);
    }
}
