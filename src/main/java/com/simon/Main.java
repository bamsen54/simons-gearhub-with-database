package com.simon;

import com.simon.entity.*;
import com.simon.repo.*;
import com.simon.service.RentalService;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main  {

    void main() {
        MemberRepoImpl memberRepo = new MemberRepoImpl();
        RentalRepoImpl rentalRepo = new RentalRepoImpl();
        BikeRepoImpl bikeRepo = new BikeRepoImpl();
        KayakRepo kayakRepo = new KayakRepoImpl();
        TentRepoImpl tentRepo = new TentRepoImpl();

        RentalService rentalService = new RentalService( rentalRepo, memberRepo, bikeRepo, kayakRepo, tentRepo );

        Member member = memberRepo.findById( 1L ).get();

        Rental rental = new Rental( RentalType.BIKE, 1L, LocalDateTime.now(), member );

        rentalService.processNewRental(  rental );
    }
}
