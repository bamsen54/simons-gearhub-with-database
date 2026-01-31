package com.simon.repo;

import com.simon.entity.Member;
import com.simon.entity.Rental;
import com.simon.exception.EmailAlreadyTakenException;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class RentalRepoImpl implements RentalRepo {

    @Override
    public void save(Rental entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.persist( entity );

        transaction.commit();
        session.close();
    }

    @Override
    public Optional<Rental> findById(Long id) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Rental rentalWithId = session.get( Rental.class, id );

        transaction.commit();
        session.close();

        return Optional.ofNullable( rentalWithId );
    }


    @Override
    public List<Rental> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Rental> allRentals = session.createQuery( "from Rental" ).list();

        transaction.commit();
        session.close();

        return allRentals;
    }

    @Override
    public void delete(Rental entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.delete( entity );

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteById(Long id) {

    }
}
