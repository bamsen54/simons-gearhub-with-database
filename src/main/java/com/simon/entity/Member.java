package com.simon.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @OneToMany( mappedBy = "member", cascade = CascadeType.ALL)
    List<Rental> rentals = new ArrayList<>();

    public Member() {

    }

    public Member(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public Member setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Member setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Member setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Member setEmail(String email) {
        this.email = email;
        return this;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public Member setRentals(List<Rental> rentals) {
        this.rentals = rentals;
        return this;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "id = " ). append( this.id ).append( "\n" );
        stringBuilder.append( "first name = " ). append( this.firstName ).append( "\n" );
        stringBuilder.append( "last name = " ). append( this.lastName ).append( "\n" );
        stringBuilder.append( "email = " ). append( this.email ).append( "\n" );

        return stringBuilder.toString();
    }
}
