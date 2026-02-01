package com.simon.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Tent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int capacity;

    @Column
    private double weight;

    @Column
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column
    private ItemStatus status;

    public Tent() {
    }

    public Tent(int capacity, double weight, BigDecimal price, ItemStatus status) {
        this.capacity = capacity;
        this.weight   = weight;
        this.price    = price;
        this.status   = status;
    }

    public Long getId() {
        return id;
    }

    public Tent setId(Long id) {
        this.id = id;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public Tent setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Tent setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Tent setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Tent setStatus(ItemStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Tent{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", weight=" + weight +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}