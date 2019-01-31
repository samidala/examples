package com.techdisqus;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {


    private static SessionFactory sessionFactory;
    public static SessionFactory buildSessionFactory(){

        if(sessionFactory == null){
            StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
            StandardServiceRegistry standardServiceRegistry =  standardServiceRegistryBuilder.configure().build();
            MetadataSources sources = new MetadataSources(standardServiceRegistry);
            // Create Metadata
            Metadata metadata = sources.getMetadataBuilder().build();
            // Create SessionFactory
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }
        return sessionFactory;
    }
}
