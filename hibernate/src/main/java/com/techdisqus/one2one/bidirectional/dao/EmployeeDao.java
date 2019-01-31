package com.techdisqus.one2one.bidirectional.dao;

import com.techdisqus.HibernateUtil;
import com.techdisqus.one2one.bidirectional.model.Address;
import com.techdisqus.one2one.bidirectional.model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeDao {

    public Employee save(Employee employee){

        Session session = HibernateUtil.buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        Address address = employee.getAddress().setId(employee.getId());
        employee.setAddress(address);
        session.save(address);
        session.flush();
        transaction.commit();

        return employee;
    }

    public Employee update(Employee employee){

        Session session = HibernateUtil.buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();
        return employee;
    }

    public Employee merge(Employee employee){

        Session session = HibernateUtil.buildSessionFactory().openSession();
        Employee employee1 = (Employee) session.merge(employee);
        return employee1;
    }

    public Employee delete(Employee employee){
        Session session = HibernateUtil.buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(employee);
        transaction.commit();
        return employee;
    }

    public List<Employee> findEmployeesByCityUsingHqlUsingJoinFetch(String city){

        String hql = "from Employee e join fetch e.address a where e.address.cityName = :city";

        List<Employee> employees = getSession().createQuery(hql, Employee.class).setParameter("city",city).getResultList();
        return employees;

    }

    public List<Employee> findEmployeesByCityUsingHqlUsingJoin(String city){

        String hql = "from Employee e where e.address.cityName = :city";

        List<Employee> employees = getSession().createQuery(hql, Employee.class).setParameter("city",city).getResultList();
        return employees;

    }


    public List<Employee> findEmployeesByCityUsingCriteria(String city){
        Session session = getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.createAlias("address","a", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.eq("a.cityName",city));
        List<Employee> employees = criteria.list();
        return employees;
    }


    public List<Employee> findEmployeesByCityUsingJpaCriteriaQuery(String city){
        Session session = getSession();

        CriteriaBuilder criteriaBuilder  = session.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);
        employeeCriteriaQuery.where(criteriaBuilder.equal(employeeRoot.join("address").get("cityName"),city ));
        List<Employee> employees = session.createQuery(employeeCriteriaQuery).getResultList();

        return employees;
    }

    public List<Employee> findEmployeesByCityUsingJpaCriteriaQueryJoinFetch(String city){
        Session session = getSession();

        CriteriaBuilder criteriaBuilder  = session.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        Fetch<Employee, Address> employeeAddressFetch = employeeRoot.fetch("address", javax.persistence.criteria.JoinType.INNER);

        employeeCriteriaQuery.select(employeeRoot);
        employeeCriteriaQuery.where(criteriaBuilder.equal(employeeRoot.join("address",
                javax.persistence.criteria.JoinType.INNER).get("cityName"),city ));
        List<Employee> employees = session.createQuery(employeeCriteriaQuery).getResultList();

        return employees;
    }

    private Session getSession(){
        return HibernateUtil.buildSessionFactory().openSession();
    }
    private SessionFactory getSessionFactory(){

        return HibernateUtil.buildSessionFactory();
    }


}
