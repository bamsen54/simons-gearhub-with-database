package com.simon.service;

import com.simon.entity.Bike;
import com.simon.entity.Kayak;
import com.simon.entity.Tent;
import com.simon.repo.BikeRepo;
import com.simon.repo.KayakRepo;
import com.simon.repo.TentRepo;
import com.simon.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;



public class InventoryService {

    private BikeRepo bikeRepo;
    private KayakRepo kayakRepo;
    private TentRepo tentRepo;

    public InventoryService() {
    }

    public InventoryService(BikeRepo bikeRepo, KayakRepo kayakRepo, TentRepo tentRepo) {
        this.bikeRepo  = bikeRepo;
        this.kayakRepo = kayakRepo;
        this.tentRepo  = tentRepo;
    }

    public <T> List<T> findAll( Class<T> type ) {

        if( type == Bike.class )
            return (List<T>) bikeRepo.findAll();

        else if(  type == Kayak.class )
            return (List<T>) kayakRepo.findAll();

        else if( type == Tent.class )
            return (List<T>) tentRepo.findAll();

        return null;
    }

    public <T> void save(T entity) {

        try (Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public <T> void update(T entity) {

        try (Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public <T> void delete(T entity) {

        try (Session session = HibernateUtil.getSessionFactory().openSession() ) {
            Transaction tx = session.beginTransaction();
            session.delete( entity );
            tx.commit();
        }
    }



}
