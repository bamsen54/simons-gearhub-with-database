package com.simon.util;

import com.simon.entity.Member;
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
                // 1. Create a registry from hibernate.cfg.xml
                StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure( "hibernate.cfg.xml" ) // Loads hibernate.cfg.xml
                        .build();

                // 2. Create MetadataSources from the registry
                MetadataSources sources = new MetadataSources(registry);

                // 3. Create Metadata (the mapping info)
                Metadata metadata = sources.getMetadataBuilder().build();

                sources.addAnnotatedClass(Member.class);

                // 4. Build the SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                // If something goes wrong, we print it
                IO.println( "something wen wrong" );
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