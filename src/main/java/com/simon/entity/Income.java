package com.simon.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table( name = "income" )
public class Income {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( name = "amount" )
    BigDecimal amount;

    @Column( name = "date")
    LocalDateTime date;

    @OneToOne( mappedBy = "income" )
    private Rental rental;

    public Income() {}

    public Income(BigDecimal amount, LocalDateTime date, Rental rental) {
        this.amount = amount;
        this.date   = date;
        this.rental = rental;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Income setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Income setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Income setId(Long id) {
        this.id = id;
        return this;
    }

    public Rental getRental() {
        return rental;
    }

    public Income setRental(Rental rental) {
        this.rental = rental;
        return this;
    }

    @Override
    public String toString() {
        return "Income{" +
                "id=" + id +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
