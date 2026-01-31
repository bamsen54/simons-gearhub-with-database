package com.simon.repo;

import com.simon.entity.Member;
import com.simon.exception.EmailAlreadyTakenException;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MemberRepoImpl implements MemberRepo {

    public Optional<Member> findByEmail(String email) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            Member member = (Member) session.createQuery("FROM Member WHERE email = :email")
                                            .setParameter("email", email).uniqueResult();

            return Optional.ofNullable(member);
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void save(Member entity) {

        if( findByEmail( entity.getEmail()).isPresent() )
            throw new EmailAlreadyTakenException( "email = " + entity.getEmail() );

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
    public void update(Member entity) {

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
    public Optional<Member> findById(Long id) {

        try ( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Member member = session.get(Member.class, id);
            return Optional.ofNullable( member );
        }

        catch ( Exception e ) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Member> findAll() {

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            return session.createQuery("from Member").getResultList();
        }

        catch ( Exception e ) {
            return List.of();
        }
    }

    @Override
    public void delete(Member entity) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {

            transaction = session.beginTransaction();
            session.delete( entity );
            transaction.commit();
        }

        catch ( Exception e ) {

            if( transaction != null )
                transaction.rollback();
        }
    }

    @Override
    public void deleteById(Long id) {

        Transaction transaction = null;

        try( Session session = HibernateUtil.getSessionFactory().openSession() ) {
            transaction = session.beginTransaction();

            Member member = session.get(Member.class, id);

            if( member != null )
                session.delete( member );

            transaction.commit();
        }

        catch (Exception e ) {
            if( transaction != null )
                transaction.rollback();
            e.printStackTrace();
        }

    }
}
