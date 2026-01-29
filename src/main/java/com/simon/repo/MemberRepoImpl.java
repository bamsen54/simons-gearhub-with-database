package com.simon.repo;

import com.simon.entity.Member;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MemberRepoImpl implements MemberRepo {
    @Override
    public void save(Member entity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.save( entity );

        transaction.commit();
        session.close();
    }

    @Override
    public Optional<Member> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Member memberWithId = session.get( Member.class, id );

        transaction.commit();
        session.close();

        return Optional.ofNullable( memberWithId );
    }

    @Override
    public List<Member> findAll() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<Member> allMembers = session.createQuery( "from Member" , Member.class).getResultList();

        transaction.commit();
        session.close();

        return allMembers;
    }

    @Override
    public void delete(Member entity) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.delete( entity );

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Member memberWithId = session.get( Member.class, id );
        session.delete( memberWithId );

        transaction.commit();
        session.close();
    }
}
