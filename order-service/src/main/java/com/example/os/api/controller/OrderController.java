package com.example.os.api.controller;

import com.example.os.api.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.os.api.common.TransactionRequest;
import com.example.os.api.common.TransactionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException{
        return service.saveOrder(request);

        // do a rest call to payment and pass the order id
    }
}
