package com.simon.repo;

import com.simon.entity.Rental;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class RentalRepoImpl implements RentalRepo {


    @Override
    public void save(Rental entity) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            transaction = session.beginTransaction();

            session.persist( entity );
            transaction.commit();
        }

        catch ( Exception e ) {

            if( transaction != null )
                transaction.rollback();
        }
    }

    @Override
    public void update(Rental entity) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            transaction = session.beginTransaction();

            session.merge( entity );
            transaction.commit();
        }

        catch ( Exception e ) {

            if( transaction != null )
                transaction.rollback();
        }
    }

    @Override
    public Optional<Rental> findById(Long id) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Rental rental = session.get( Rental.class, id );
            return Optional.ofNullable( rental );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Rental> findAll() {

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            return session.createQuery("from Rental").getResultList();
        }

        catch ( Exception e ) {
            return List.of();
        }
    }

    @Override
    public void delete(Rental entity) {

        deleteById( entity.getId() );
    }

    @Override
    public void deleteById(Long id) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();

            Rental rental = session.get( Rental.class, id );

            if( rental != null )
                session.delete( rental );

            transaction.commit();
        }

        catch (Exception e ) {
            if( transaction != null )
                transaction.rollback();
            e.printStackTrace();
        }
    }
}
