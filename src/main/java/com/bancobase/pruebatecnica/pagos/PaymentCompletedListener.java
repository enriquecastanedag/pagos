package com.bancobase.pruebatecnica.pagos;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentCompletedListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "payment-status-queue")
    public void listen(String message) {
        try {
            JsonNode node = objectMapper.readTree(message);
            String status = node.get("status").asText();
            if ("COMPLETED".equalsIgnoreCase(status)) {
                System.out.println("[COMPLETED CONSUMER] Payment completed: " + message);               
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
