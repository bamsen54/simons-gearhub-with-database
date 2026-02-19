package com.simon.util;

import com.simon.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .build();

                MetadataSources sources = new MetadataSources(registry);

                sources.addAnnotatedClass( Member.class );
                sources.addAnnotatedClass( Rental.class );
                sources.addAnnotatedClass( Income.class );
                sources.addAnnotatedClass( Bike.class );
                sources.addAnnotatedClass( Kayak.class );
                sources.addAnnotatedClass( Tent.class );


                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                IO.println("Something went wrong");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}