package com.simon.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Bike {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column
    private String name;

    @Column
    private String bikeType;

    @Column
    private int gearCount;

    @Column
    BigDecimal price;

    @Enumerated( EnumType.STRING )
    @Column
    private ItemStatus status;

    public Bike() {
    }

    public Bike(String name, String bikeType, int gearCount, BigDecimal price, ItemStatus status) {
        this.name      = name;
        this.bikeType  = bikeType;
        this.gearCount = gearCount;
        this.price     = price;
        this.status    = status;
    }

    public Long getId() {
        return id;
    }

    public Bike setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Bike setName(String name) {
        this.name = name;
        return this;
    }

    public String getBikeType() {
        return bikeType;
    }

    public Bike setBikeType(String bikeType) {
        this.bikeType = bikeType;
        return this;
    }

    public int getGearCount() {
        return gearCount;
    }

    public Bike setGearCount(int gearCount) {
        this.gearCount = gearCount;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Bike setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Bike setStatus(ItemStatus status) {
        this.status = status;
        return this;
    }


    @Override
    public String toString() {
        return "Bike{" +
                "bikeType='" + bikeType + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", gearCount=" + gearCount +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
