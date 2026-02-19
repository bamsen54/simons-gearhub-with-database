package com.simon.entity;

import javafx.beans.property.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "bikes")
public class Bike {

    private Long id;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty bikeType = new SimpleStringProperty();
    private IntegerProperty gearCount = new SimpleIntegerProperty();
    private ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
    private ObjectProperty<ItemStatus> status = new SimpleObjectProperty<>();

    public Bike() {
    }

    public Bike(String name, String bikeType, int gearCount, BigDecimal price, ItemStatus status) {
        this.name.set(name);
        this.bikeType.set(bikeType);
        this.gearCount.set(gearCount);
        this.price.set(price);
        this.status.set(status);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @Column
    public String getBikeType() {
        return bikeType.get();
    }

    public void setBikeType(String bikeType) {
        this.bikeType.set(bikeType);
    }

    public StringProperty bikeTypeProperty() {
        return bikeType;
    }

    @Column
    public int getGearCount() {
        return gearCount.get();
    }

    public void setGearCount(int gearCount) {
        this.gearCount.set(gearCount);
    }

    public IntegerProperty gearCountProperty() {
        return gearCount;
    }

    @Column
    public BigDecimal getPrice() {
        return price.get();
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    @Enumerated(EnumType.STRING)
    @Column
    public ItemStatus getStatus() {
        return status.get();
    }

    public void setStatus(ItemStatus status) {
        this.status.set(status);
    }

    public ObjectProperty<ItemStatus> statusProperty() {
        return status;
    }
}