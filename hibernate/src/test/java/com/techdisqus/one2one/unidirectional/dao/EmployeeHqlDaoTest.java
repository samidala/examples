package com.techdisqus.one2one.unidirectional.dao;

import com.techdisqus.one2one.unidirectional.model.Address;
import com.techdisqus.one2one.unidirectional.model.Country;
import com.techdisqus.one2one.unidirectional.model.Employee;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EmployeeHqlDaoTest {

    @Test
    public void testSave(){

        Country country = new Country().setName("IND").setCountryCode(1);


        Address address = new Address().setCityName("BLR").setStateName("KA")
                .setStreetName("NAGARBAVI").setZipCode("12344").setCountry(country);
        Employee employee = new Employee().setName("shiva").setSalary(123.32)
                                        .setAddress(address);


        EmployeeDao employeeDao = new EmployeeDao();
        employeeDao.save(employee);

    }

    @Test
    public void testFindEmployeesByCityUsingHqlJoinFetch(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCityUsingHqlUsingJoinFetch("BLR");
        System.out.println(employees);
        Assert.assertNotNull(employees);

    }

    @Test
    public void testFindEmployeesByCityUsingHqlJoin(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCityUsingHqlUsingJoin("BLR");
        System.out.println(employees);
        Assert.assertNotNull(employees);

    }

    @Test
    public void testFindEmployeesByCityUsingCriteria(){
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCityUsingCriteria("BLR");
        System.out.println(employees);
        Assert.assertNotNull(employees);
    }


    @Test
    public void testFindEmployeesByCityUsingJpaCriteriaQuery(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCityUsingJpaCriteriaQuery("BLR");
        System.out.println(employees);
        Assert.assertNotNull(employees);

    }

    @Test
    public void testFindEmployeesByCityUsingJpaCriteriaQueryJoinFetch(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCityUsingJpaCriteriaQueryJoinFetch("BLR");
        System.out.println(employees);
        Assert.assertNotNull(employees);

    }


    @Test
    public void testFindEmployeesByCountryUsingHql(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingHql("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);


    }


    @Test
    public void testFindEmployeesByCountryUsingHqlEagerFetch(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingHqlEagerFetch("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);


    }
    @Test
    public void testFindEmployeesByCountryUsingHqlEagerFetchIncludingCountry(){

        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingHqlEagerFetchIncludingCountry("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);


    }


    @Test
    public void testFindEmployeesByCountryUsingCriteria(){
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingCriteria("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);

    }


    @Test
    public void testFindEmployeesByCountryUsingJpaCriteria(){
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingJpaCriteria("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);

    }

    @Test
    public void testFindEmployeesByCountryUsingJpaCriteriaFetch(){
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> employees = employeeDao.findEmployeesByCountryUsingJpaCriteriaFetch("1");
        System.out.println(employees.size());
        Assert.assertNotNull(employees);

    }
   static EmployeeDao employeeDao = new EmployeeDao();

    @Test
    public void testselectEmployeesUsingHql(){
        List<Employee> employees = employeeDao.selectEmployeeUsingHql();
        Assert.assertNotNull(employees);
        System.out.println(employees.size());
    }

    @Test
    public void testselectEmployeesUsingCriteria(){
        List<Employee> employees = employeeDao.selectEmployeeUsingCriteria();
        Assert.assertNotNull(employees);
        System.out.println(employees.size());
    }

    @Test
    public void testselectEmployeesUsingJpaCriteriaBuilder(){
        List<Employee> employees = employeeDao.selectEmployeeUsingJpaCriteria();
        Assert.assertNotNull(employees);
        System.out.println(employees.size());
    }

    @Test
    public void testselectOnlyNameUsingCriteria(){
        List<String> employees = employeeDao.selectOnlyNameFromEmployeeUsingCriteria();
        Assert.assertNotNull(employees);
        System.out.println(employees);
    }

    @Test
    public void testselectOnlyNameFromEmployeeUsingCriteriaJpa(){
        List<Employee > employees = employeeDao.selectOnlyNameFromEmployeeUsingCriteriaJpa();
        Assert.assertNotNull(employees);
        System.out.println(employees);
    }


    @Test
    public void testgetSumOfAllSalariesUsingHql(){
        double salariesSum = employeeDao.getSumOfAllSalariesUsingHql();
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByNameUsingHql(){
        double salariesSum = employeeDao.getSumOfAllSalariesByNameUsingHql("shiva");
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByZipCodeUsingHqlInvalidInput(){
        double salariesSum = employeeDao.getSumOfAllSalariesByZipCodeUsingHql("shiva");
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByZipCodeUsingHql(){
        double salariesSum = employeeDao.getSumOfAllSalariesByZipCodeUsingHql("12344");
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }


    @Test
    public void testgetSumOfAllSalariesByCountryCodeUsingHql(){
        double salariesSum = employeeDao.getSumOfAllSalariesByCountryCodeUsingHql(1);
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }


    @Test
    public void testgetSumOfAllSalariesUsingCriteria(){
        double salariesSum = employeeDao.getSumOfAllSalariesUsingCriteria();
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesUsingCriteriaJpa(){
        double salariesSum = employeeDao.getSumOfAllSalariesUsingCriteriaJpa();
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }



    @Test
    public void testgetSumOfAllSalariesByZipCodeUsingCriteria(){
        double salariesSum = employeeDao.getSumOfAllSalariesByZipCodeUsingCriteria("12344");
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByZipCodeUsingCriteriaJpa(){
        double salariesSum = employeeDao.getSumOfAllSalariesByZipCodeUsingCriteriaJpa("12344");
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByCountryCodeUsingCriteria(){
        double salariesSum = employeeDao.getSumOfAllSalariesByCountryCodeUsingCriteria(1);
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }

    @Test
    public void testgetSumOfAllSalariesByCountryCodeUsingCriteriaJpa(){
        double salariesSum = employeeDao.getSumOfAllSalariesByCountryCodeUsingCriteriaJpa(1);
        Assert.assertNotNull(salariesSum);
        System.out.println(salariesSum);
    }



















}
