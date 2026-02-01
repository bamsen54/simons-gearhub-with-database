package com.simon.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Kayak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nama;

    @Column
    private int numberOfSeats;

    @Column
    private boolean hasRudder;

    @Column
    private BigDecimal price;

    @Enumerated( EnumType.STRING )
    @Column
    private ItemStatus status;

    public Kayak() {
    }

    public Kayak(String name, int numberOfSeats, boolean hasRudder, BigDecimal price, ItemStatus status) {
        this.nama          = name;
        this.numberOfSeats = numberOfSeats;
        this.hasRudder     = hasRudder;
        this.price         = price;
        this.status        = status;
    }

    public Long getId() {
        return id;
    }

    public Kayak setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNama() {
        return nama;
    }

    public Kayak setNama(String nama) {
        this.nama = nama;
        return this;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public Kayak setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public boolean isHasRudder() {
        return hasRudder;
    }

    public Kayak setHasRudder(boolean hasRudder) {
        this.hasRudder = hasRudder;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Kayak setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public Kayak setStatus(ItemStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Kayak{" +
                "hasRudder=" + hasRudder +
                ", id=" + id +
                ", nama='" + nama + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
