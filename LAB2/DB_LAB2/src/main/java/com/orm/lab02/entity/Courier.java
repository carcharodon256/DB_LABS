package com.orm.lab02.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courierId;
    private String courierPhoneNumber;
    private String courierName;
    private String transportKind;
    private double courierRating;

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getCourierPhoneNumber() {
        return courierPhoneNumber;
    }

    public void setCourierPhoneNumber(String courierPhoneNumber) {
        this.courierPhoneNumber = courierPhoneNumber;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getTransportKind() {
        return transportKind;
    }

    public void setTransportKind(String transportKind) {
        this.transportKind = transportKind;
    }

    public double getCourierRating() {
        return courierRating;
    }

    public void setCourierRating(double courierRating) {
        this.courierRating = courierRating;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "courierPhoneNumber='" + courierPhoneNumber + '\'' +
                ", courierName='" + courierName + '\'' +
                ", transportKind='" + transportKind + '\'' +
                ", courierRating=" + courierRating +
                '}';
    }
}