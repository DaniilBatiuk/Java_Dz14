package com.example.lab14;

public class Order {
    private int id;
    private String customerName;
    private String phoneNumber;
    private String email;
    private String deliveryAddress;
    private int pizzaId;

    public Order(int id, String customerName, String phoneNumber, String email, String deliveryAddress, int pizzaId) {
        this.id = id;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.pizzaId = pizzaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }
}


