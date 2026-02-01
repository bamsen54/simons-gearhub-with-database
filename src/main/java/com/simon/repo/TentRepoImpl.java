package com.simon.repo;

import com.simon.entity.Tent;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TentRepoImpl implements TentRepo {

    @Override
    public void save(Tent entity) {

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
    public void update(Tent entity) {

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
    public Optional<Tent> findById(Long id) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Tent tent = session.get( Tent.class, id );
            return Optional.ofNullable( tent );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Tent> findAll() {

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            return session.createQuery("from Tent ").getResultList();
        }

        catch ( Exception e ) {
            return List.of();
        }
    }

    @Override
    public void delete(Tent entity) {

        deleteById( entity.getId() );
    }

    @Override
    public void deleteById(Long id) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();

            Tent tent = session.get( Tent.class, id );

            if( tent != null )
                session.delete( tent  );

            transaction.commit();
        }

        catch (Exception e ) {
            if( transaction != null )
                transaction.rollback();
            e.printStackTrace();
        }
    }
}


