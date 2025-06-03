package com.bancobase.pruebatecnica.pagos.controller;

import com.bancobase.pruebatecnica.pagos.model.Payment;
import com.bancobase.pruebatecnica.pagos.model.User;
import com.bancobase.pruebatecnica.pagos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{account}/payments")
public class PaymentController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Payment> getPayments(@PathVariable String account) {
        return userService.getPayments(account);
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentById(@PathVariable String account, @PathVariable String paymentId) {
        return userService.getPaymentById(account, paymentId);
    }

    @GetMapping("/{paymentId}/status")
    public String getPaymentStatus(
            @PathVariable String account,
            @PathVariable String paymentId) {
        return userService.getPaymentStatus(account, paymentId);
    }

    @PostMapping
    public User addPaymentToUser(
            @PathVariable String account,
            @RequestBody Payment payment) {
        double total = 0.0;
        if (payment.getProducts() != null) {
            for (Payment.ProductQuantity pq : payment.getProducts()) {
                if (pq.getProduct() != null && pq.getProduct().getPrice() != null) {
                    total += pq.getProduct().getPrice() * pq.getQuantity();
                }
            }
        }
        payment.setAmount(total);
        return userService.addPaymentToUser(account, payment);
    }

    @PatchMapping("/{paymentId}/status")
    public User updatePaymentStatus(
            @PathVariable String account,
            @PathVariable String paymentId,
            @RequestBody String status) {
        User updatedUser = userService.updatePaymentStatus(account, paymentId, status);
        userService.notifyPaymentStatusChanged(account, paymentId, status);
        return updatedUser;
    }
}

