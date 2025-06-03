package com.bancobase.pruebatecnica.pagos.service;

public class PaymentStatusChangedEvent implements java.io.Serializable{
    private String account;
    private String paymentId;
    private String status;

    public PaymentStatusChangedEvent(String account, String paymentId, String status) {
        this.account = account;
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}