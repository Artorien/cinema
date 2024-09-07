package com.example.Practice;

public class PaymentBody {
    private String cardNumber;
    private String cvc;
    private String date;
    private String price;
    private String id;

    public PaymentBody() {

    }

    public PaymentBody(String cardNumber, String cvc, String date, String price, String id) {
        this.cardNumber = cardNumber;
        this.cvc = cvc;
        this.date = date;
        this.price = price;
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}