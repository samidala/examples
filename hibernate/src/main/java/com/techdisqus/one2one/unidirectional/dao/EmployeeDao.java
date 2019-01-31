package com.techdisqus.one2one.unidirectional.dao;


import com.techdisqus.HibernateUtil;

import com.techdisqus.one2one.unidirectional.model.Address;
import com.techdisqus.one2one.unidirectional.model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeDao {

    public Employee save(Employee employee){

        Session session = HibernateUtil.buildSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(employee);
        Address address = employee.getAddress().setId(employee.getId());
        employee.setAddress(address);
        session.saveOrUpdate(employee.getAddress().getCountry());
        session.saveOrUpdate(address);
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

    public List<Employee> findEmployeesByCityUsingHqlUsingJoin(String city){

        String hql = "from Employee e where e.address.cityName = :city";

        List<Employee> employees = getSession().createQuery(hql,Employee.class).setParameter("city",city).getResultList();
        return employees;

    }

    public List<Employee> findEmployeesByCityUsingHqlUsingJoinFetch(String city){

        String hql = "from Employee e join fetch e.address a where e.address.cityName = :city";

        List<Employee> employees = getSession().createQuery(hql,Employee.class).setParameter("city",city).getResultList();
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
        Fetch<Employee,Address> employeeAddressFetch = employeeRoot.fetch("address", javax.persistence.criteria.JoinType.INNER);

        employeeCriteriaQuery.select(employeeRoot);

        employeeCriteriaQuery.where(criteriaBuilder.equal(employeeRoot.join("address",
                javax.persistence.criteria.JoinType.INNER).get("cityName"),city ));
        List<Employee> employees = session.createQuery(employeeCriteriaQuery).getResultList();

        return employees;
    }

    public List<Employee> findEmployeesByCountryUsingHql(String countryCode){

        String hql = " from Employee e  where e.address.country.countryCode = :countryCode";
        return getSession().createQuery(hql,Employee.class).setParameter("countryCode",Integer.parseInt(countryCode)).list();

    }

    public List<Employee> findEmployeesByCountryUsingHqlEagerFetch(String countryCode){

        String hql = " from Employee e join fetch  e.address a where a.country.countryCode = :countryCode";
        return getSession().createQuery(hql,Employee.class).setParameter("countryCode",Integer.parseInt(countryCode)).list();

    }

    public List<Employee> findEmployeesByCountryUsingHqlEagerFetchIncludingCountry(String countryCode){

        String hql = " from Employee e left join fetch  e.address a left join fetch a.country c where c.countryCode = :countryCode";
        return getSession().createQuery(hql,Employee.class).setParameter("countryCode",Integer.parseInt(countryCode)).list();

    }

    public List<Employee> findEmployeesByCountryUsingCriteria(String countryCode){

        Session session = getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.createAlias("address","a");
        criteria.createAlias("a.country","c", JoinType.INNER_JOIN);
        criteria.add(Restrictions.eq("c.countryCode",Integer.parseInt(countryCode)));
        return criteria.list();

    }

    public List<Employee> findEmployeesByCountryUsingJpaCriteria(String countryCode){
        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);
        Root<Employee> employeeRoot = cq.from(Employee.class);
        cq.select(employeeRoot);
        employeeRoot.alias("e");
        Join<Employee,Address> employeeAddressJoin = employeeRoot.join("address");
        Join<Employee,Address> employeeAddressJoin1 = employeeAddressJoin.join("country");
        //employeeAddressJoin.fetch("country");

       // cq.where(cb.equal(employeeAddressJoin.join("country", javax.persistence.criteria.JoinType.INNER).get("countryCode"),Integer.parseInt(countryCode)));
        cq.where(cb.equal(employeeAddressJoin1.get("countryCode"),Integer.parseInt(countryCode)));

        return session.createQuery(cq).list();

    }

    public List<Employee> findEmployeesByCountryUsingJpaCriteriaFetch(String countryCode){

        Session session = getSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = cb.createQuery(Employee.class);

        Root<Employee> employeeRoot =  criteriaQuery.from(Employee.class);
        employeeRoot.fetch("address");

        Join<Employee,Address> employeeAddressJoin = employeeRoot.join("address");

        criteriaQuery.select(employeeRoot);
        //employeeAddressJoin.fetch("country");
        criteriaQuery.where(cb.equal(employeeAddressJoin.get("country"),Integer.parseInt(countryCode)));
        return session.createQuery(criteriaQuery).list();

    }

    public List<Employee> selectEmployeeUsingHql(){

        Session session = getSession();

        String hql = " from Employee";
        return session.createQuery(hql,Employee.class).setMaxResults(1).setFirstResult(1). getResultList();
    }

    public List<Employee> selectEmployeeUsingCriteria(){

        Session session = getSession();

        Criteria criteria = session.createCriteria(Employee.class);
        String hql = " from Employee";
        criteria.addOrder(Order.asc("name"));
        criteria.setFirstResult(1).setMaxResults(1);
        return criteria.list();
    }
    public List<Employee> selectEmployeeUsingJpaCriteria(){
        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.select(employeeRoot);

        return  session.createQuery(employeeCriteriaQuery).setMaxResults(1).setFirstResult(1).list();

    }

    public List<String> selectOnlyNameFromEmployeeUsingCriteria(){

        Session session = getSession();

        Criteria criteria = session.createCriteria(Employee.class);

        ProjectionList projection = Projections.projectionList();

        projection.add(Projections.property("name"));
        criteria.setProjection(projection);
        criteria.addOrder(Order.asc("name"));

        criteria.setFirstResult(1).setMaxResults(1);
        return criteria.list();
    }

    public List<Employee> selectOnlyNameFromEmployeeUsingCriteriaJpa(){

        Session session = getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot =employeeCriteriaQuery.from(Employee.class);
        employeeCriteriaQuery.multiselect(employeeRoot.get("name"),employeeRoot.get("salary"));

        return  session.createQuery(employeeCriteriaQuery).setMaxResults(1).list();
    }

    public double getSumOfAllSalariesUsingHql(){

        String hql = "SELECT SUM(salary) FROM Employee";
        Session session = getSession();
        return  session.createQuery(hql,Double.class).uniqueResult();
    }

    public double getSumOfAllSalariesByNameUsingHql(String name){

        String hql = "SELECT SUM(salary) FROM Employee e  where  e.name = :name";
        Session session = getSession();
        System.out.println(session.createQuery(hql,Double.class).setParameter("name",name));
        return  session.createQuery(hql,Double.class).setParameter("name",name).uniqueResult();
    }


    public double getSumOfAllSalariesUsingCriteria(){


        Session session = getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        Projection sumProjection = Projections.sum("salary");
        criteria.setProjection(sumProjection);

        return (double) criteria.uniqueResult();
    }

    public double getSumOfAllSalariesUsingCriteriaJpa(){


        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Double> doubleCriteriaQuery = criteriaBuilder.createQuery(Double.class);

        Root<Employee> employeeRoot = doubleCriteriaQuery.from(Employee.class);

        doubleCriteriaQuery.select(criteriaBuilder.sumAsDouble(employeeRoot.get("salary")));

        return session.createQuery(doubleCriteriaQuery).uniqueResultOptional().orElse(-1.0).doubleValue();


        //return session.createQuery(employeeCriteriaQuery).uniqueResultOptional().or(-1).get().
    }




    public double getSumOfAllSalariesByZipCodeUsingHql(String zipCode){

        String hql = "SELECT SUM(salary) FROM Employee e  where  e.address.zipCode = :zipCode";
        Session session = getSession();

        return  session.createQuery(hql,Double.class).setParameter("zipCode",zipCode).uniqueResultOptional().orElse(-1.0d).doubleValue();
    }
    public double getSumOfAllSalariesByZipCodeUsingCriteria(String zipCode){
        Session session = getSession();
        Criteria criteria = session.createCriteria(Employee.class);
        Projection projection = Projections.sum("salary");

        return (double)criteria.createAlias("address","a").setProjection(projection)
                .add(Restrictions.eq("a.zipCode",zipCode)).uniqueResult();

    }

    public double getSumOfAllSalariesByZipCodeUsingCriteriaJpa(String zipCode){
        Session session = getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Double> cr = cb.createQuery(Double.class);
        Root<Employee> employeeRoot = cr.from(Employee.class);
        cr.where(cb.equal(employeeRoot.join("address").get("zipCode"),zipCode));
        cr.select(cb.sumAsDouble(employeeRoot.get("salary")));
        return session.createQuery(cr).uniqueResultOptional().orElse(-1d).doubleValue();

    }



    public double getSumOfAllSalariesByCountryCodeUsingHql(int countryCode){

        String hql = "SELECT SUM(salary) FROM Employee e  where  e.address.country.countryCode = :countryCode";
        Session session = getSession();

        return  session.createQuery(hql,Double.class).setParameter("countryCode",countryCode).uniqueResultOptional().orElse(-1.0d).doubleValue();
    }



    private Session getSession(){
        return HibernateUtil.buildSessionFactory().openSession();
    }



}
