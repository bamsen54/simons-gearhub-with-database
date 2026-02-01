package com.simon.repo;

import com.simon.entity.Kayak;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class KayakRepoImpl implements KayakRepo {

    @Override
    public void save(Kayak entity) {

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
    public void update(Kayak entity) {

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
    public Optional<Kayak> findById(Long id) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Kayak kayak = session.get( Kayak.class, id );
            return Optional.ofNullable( kayak );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Kayak> findAll() {

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            return session.createQuery("from Kayak ").getResultList();
        }

        catch ( Exception e ) {
            return List.of();
        }
    }

    @Override
    public void delete(Kayak entity) {

        deleteById( entity.getId() );
    }

    @Override
    public void deleteById(Long id) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();

            Kayak kayak = session.get( Kayak.class, id );

            if( kayak != null )
                session.delete( kayak );

            transaction.commit();
        }

        catch (Exception e ) {
            if( transaction != null )
                transaction.rollback();
            e.printStackTrace();
        }

    }
}
