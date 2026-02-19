package com.simon.repo;

import com.simon.entity.Bike;
import com.simon.entity.Rental;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class BikeRepoImpl implements BikeRepo {

    @Override
    public void save(Bike entity) {

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
    public void update(Bike entity) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            transaction = session.beginTransaction();
            session.merge( entity );
            transaction.commit();
        }

        catch ( Exception e ) {

            if( transaction != null ) {
                transaction.rollback();
            }
        }
    }

    @Override
    public Optional<Bike> findById(Long id) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Bike bike = session.get( Bike.class, id );
            return Optional.ofNullable( bike );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Bike> findAll() {

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            return session.createQuery("from Bike").getResultList();
        }

        catch ( Exception e ) {
            return List.of();
        }
    }

    @Override
    public void delete(Bike entity) {

        deleteById( entity.getId() );
    }

    @Override
    public void deleteById(Long id) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();

            Bike bike = session.get( Bike.class, id );

            if( bike != null )
                session.delete( bike );

            transaction.commit();
        }

        catch (Exception e ) {
            if( transaction != null )
                transaction.rollback();
            e.printStackTrace();
        }

    }
}
