package com.bancobase.pruebatecnica.pagos.model;

import java.util.List;

import lombok.Data;

@Data
public class Payment {
  
    private String id;
    private String concepto;
    private String senderAccount;
    private String receiverAccount;
    private Double amount;
    private String status;
    private List<ProductQuantity> products;

    @Data
    public static class ProductQuantity {
        private Product product;
        private int quantity;
    }
}