package com.simon.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table( name = "rentals" )
public class Rental {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;

    @Enumerated( EnumType.STRING )
    @Column(  nullable = false )
    RentalType rentalType;

    @Column(  nullable = false )
    Long rentalObjectId;

    @Column(  nullable = false, columnDefinition = "DATETIME")
    LocalDateTime rentalDate;

    @Column( precision = 0 )
    LocalDateTime returnDate;

    @ManyToOne
    @JoinColumn( name = "member_id" )
    Member member;

    public Rental() {
    }

    public Rental( RentalType rentalType, Long rentalObjectId, LocalDateTime rentalDate, Member member ) {
        this.rentalType     = rentalType;
        this.rentalObjectId = rentalObjectId;
        this.rentalDate     = rentalDate;
        this.member         = member;
    }

    public Long getId() {
        return id;
    }

    public Rental setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public Rental setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
        return this;
    }

    public Long getRentalObjectId() {
        return rentalObjectId;
    }

    public Rental setRentalObjectId(Long rentalObjectId) {
        this.rentalObjectId = rentalObjectId;
        return this;
    }

    public RentalType getRentalType() {
        return rentalType;
    }

    public Rental setRentalType(RentalType rentalType) {
        this.rentalType = rentalType;
        return this;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public Rental setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    public Member getMember() {
        return member;
    }

    public Rental setMember(Member member) {
        this.member = member;
        return this;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", type=" + rentalType +
                ", objectId=" + rentalObjectId +
                ", member=" + (member != null ? member.getLastName() : "null") +
                ", rented=" + rentalDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                ", returned=" + (returnDate != null ? returnDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "OUT") +
                '}';
    }
}
