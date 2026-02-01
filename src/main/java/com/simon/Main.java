package com.simon;

import com.simon.entity.*;
import com.simon.repo.*;
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


        // 10 Cyklar
        bikeRepo.save(new Bike("Crescent Kebne", "Hybrid", 24, new BigDecimal("250.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Skeppshult Natur", "City", 7, new BigDecimal("180.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Specialized Rockhopper", "MTB", 12, new BigDecimal("300.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Monark Karin", "Classic", 3, new BigDecimal("150.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Bianchi Via Nirone", "Road", 18, new BigDecimal("400.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Kona Dew", "Gravel", 11, new BigDecimal("220.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Trek Marlin 7", "MTB", 10, new BigDecimal("280.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Giant Defy", "Road", 20, new BigDecimal("380.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Cannondale Quick", "Hybrid", 18, new BigDecimal("240.00"), ItemStatus.AVAILABLE));
        bikeRepo.save(new Bike("Pilen Lyx", "Classic", 7, new BigDecimal("190.00"), ItemStatus.AVAILABLE));



    }
}
