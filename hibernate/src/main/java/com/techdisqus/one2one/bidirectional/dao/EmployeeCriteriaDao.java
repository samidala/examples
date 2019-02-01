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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeCriteriaDao {

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




    public List<Employee> getEmployeeByZipCodeUsingCriteria(String zipCode){
        Session session = getSession();
        Criteria criteria = session.createCriteria(Employee.class,"e")
                .createAlias("address","a").add(Restrictions.eq("a.zipCode",zipCode));

        return criteria.list();

    }



    private Session getSession(){
        return HibernateUtil.buildSessionFactory().openSession();
    }
    private SessionFactory getSessionFactory(){

        return HibernateUtil.buildSessionFactory();
    }


}
