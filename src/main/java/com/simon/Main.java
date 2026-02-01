package com.simon;

import com.simon.entity.Member;
import com.simon.entity.Rental;
import com.simon.entity.RentalType;
import com.simon.repo.MemberRepoImpl;
import com.simon.repo.RentalRepo;
import com.simon.repo.RentalRepoImpl;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main  {

    void main() {
        MemberRepoImpl memberRepo = new MemberRepoImpl();
        RentalRepoImpl rentalRepo = new RentalRepoImpl();


        Rental rental = new Rental( RentalType.BIKE, 1L, LocalDateTime.now(), memberRepo.findById( 1L).get()  );










    }
}
