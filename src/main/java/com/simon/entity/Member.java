package com.simon.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "members" )
public class Member {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Column( nullable = false, length = 64 )
    private String firstName;

    @Column( nullable = false, length = 64 )
    private String lastName;

    @Column( unique = true, length = 64 )
    private String email;

    @OneToMany( mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    private List<Rental> rentals = new ArrayList<>();

    public Member() {}

    public Member(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    public Long getIdProperty() {
        return this.id;
    }

    public StringProperty firstNameProperty() {
        return new SimpleStringProperty( firstName );
    }

    public StringProperty lastNameProperty() {
        return new SimpleStringProperty( lastName );
    }

    public StringProperty emailProperty() {
        return new SimpleStringProperty( email );
    }


    public Long getId() {
        return id;
    }
    public Member setId(Long id) { this.id = id; return this; }

    public String getFirstName() { return firstName; }
    public Member setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() { return lastName; }
    public Member setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() { return email; }
    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<Rental> getRentals() { return rentals; }
    public Member setRentals(List<Rental> rentals) {
        this.rentals = rentals;
        return this;
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " ID(" + this.id + ")";
    }

    public String getSearchString() {

        return this.id + " " + this.firstName + " " + this.lastName + this.email;
    }
}