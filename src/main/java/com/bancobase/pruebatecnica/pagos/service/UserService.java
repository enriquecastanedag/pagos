package com.bancobase.pruebatecnica.pagos.service;

import com.bancobase.pruebatecnica.pagos.model.Payment;
import com.bancobase.pruebatecnica.pagos.model.User;
import com.bancobase.pruebatecnica.pagos.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Get all payments for a user
    public List<Payment> getPayments(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.map(User::getPayments).orElse(null);
    }

    // Get a specific payment for a user
    public Payment getPaymentById(String userId, String paymentId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getPayments() != null) {
            return userOpt.get().getPayments().stream()
                    .filter(p -> paymentId.equals(p.getId()))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public User addPaymentToUser(String account, Payment payment) {
        User user = userRepository.findByAccount(account);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        if (user.getPayments() == null) {
            user.setPayments(new ArrayList<>());
        }
        user.getPayments().add(payment);
        return userRepository.save(user);
    }

    public User updatePaymentStatus(String account, String paymentId, String status) {
        User user = userRepository.findByAccount(account);
        if (user == null)
            throw new RuntimeException("User not found");
        if (user.getPayments() == null)
            throw new RuntimeException("No payments found for user");

        for (Payment payment : user.getPayments()) {
            if (payment.getId().equals(paymentId)) {
                payment.setStatus(status);
                return userRepository.save(user);
            }
        }
        throw new RuntimeException("Payment not found");
    }

    public void notifyPaymentStatusChanged(String account, String paymentId, String status) {
        PaymentStatusChangedEvent event = new PaymentStatusChangedEvent(account, paymentId, status);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            rabbitTemplate.convertAndSend("payment-status-exchange",
                    "payment.status.changed", eventJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the status of a specific payment for a user
    public String getPaymentStatus(String account, String paymentId) {
        User user = userRepository.findByAccount(account);
        if (user == null || user.getPayments() == null) {
            throw new RuntimeException("User or payments not found");
        }
        for (Payment payment : user.getPayments()) {
            if (payment.getId().equals(paymentId)) {
                return payment.getStatus();
            }
        }
        throw new RuntimeException("Payment not found");
    }
}