package com.orm.lab02.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private int orderNumber;
    private String deliveryAddress;
    private Timestamp deliveryDateTime;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "courierId")
    private Courier courier;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Timestamp getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(Timestamp deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryDateTime=" + deliveryDateTime +
                ", client=" + client +
                ", courier=" + courier +
                '}';
    }
}
