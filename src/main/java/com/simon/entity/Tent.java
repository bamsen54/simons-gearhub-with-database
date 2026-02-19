package com.simon.entity;

import javafx.beans.property.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tents")
public class Tent {

    private Long id;
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty capacity = new SimpleIntegerProperty();
    private final DoubleProperty weight = new SimpleDoubleProperty();
    private final ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
    private final ObjectProperty<ItemStatus> status = new SimpleObjectProperty<>();

    public Tent() {
    }

    public Tent(String name, int capacity, double weight, BigDecimal price, ItemStatus status) {
        setName(name);
        setCapacity(capacity);
        setWeight(weight);
        setPrice(price);
        setStatus(status);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "capacity")
    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "weight")
    public double getWeight() {
        return weight.get();
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    @Access(AccessType.PROPERTY)
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    @Access(AccessType.PROPERTY)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public ItemStatus getStatus() {
        return status.get();
    }

    public void setStatus(ItemStatus status) {
        this.status.set(status);
    }

    public ObjectProperty<ItemStatus> statusProperty() {
        return status;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getCapacity() + " " + this.getStatus() + " " + this.getPrice();
    }
}