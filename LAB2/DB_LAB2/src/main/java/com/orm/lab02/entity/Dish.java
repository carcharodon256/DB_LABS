package com.orm.lab02.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dishId;
    private String dishName;
    private int weight;
    private int price;
    private int servingsAmount;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId")
    private CustomerOrder order;

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getServingsAmount() {
        return servingsAmount;
    }

    public void setServingsAmount(int servingsAmount) {
        this.servingsAmount = servingsAmount;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void setOrder(CustomerOrder order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "dishId=" + dishId +
                ", dishName='" + dishName + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", servingsAmount=" + servingsAmount +
                ", order=" + order +
                '}';
    }
}
