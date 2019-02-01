package com.techdisqus.one2one.bidirectional.dao;

import com.techdisqus.HibernateUtil;
import com.techdisqus.one2one.bidirectional.model.BiAddress;
import com.techdisqus.one2one.bidirectional.model.BiEmployee;
import com.techdisqus.one2one.unidirectional.model.Address;
import com.techdisqus.one2one.unidirectional.model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeHqlDao {

    public BiEmployee save(BiEmployee biEmployee){

        Session session = HibernateUtil.buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(biEmployee);
        BiAddress biAddress = biEmployee.getBiAddress().setId(biEmployee.getId());
        biEmployee.setBiAddress(biAddress);
        session.save(biAddress);
        session.saveOrUpdate(biAddress.getBiCountry());
        session.flush();
        transaction.commit();

        return biEmployee;
    }


    public List<Employee> getEmployeeByZipCodeUsingHql(String zipCode){
        Session session = getSession();
        String hql = "from Person e where e.address.zipCode = :zipCode";
        return session.createQuery(hql,Employee.class).setParameter("zipCode",zipCode).getResultList();
    }


    private Session getSession(){
        return HibernateUtil.buildSessionFactory().openSession();
    }
    private SessionFactory getSessionFactory(){

        return HibernateUtil.buildSessionFactory();
    }


}
