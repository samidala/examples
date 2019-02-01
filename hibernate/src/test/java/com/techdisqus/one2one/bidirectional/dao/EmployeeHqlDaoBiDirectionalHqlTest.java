package com.techdisqus.one2one.bidirectional.dao;

import com.techdisqus.one2one.bidirectional.model.BiAddress;
import com.techdisqus.one2one.bidirectional.model.BiEmployee;
import com.techdisqus.one2one.bidirectional.model.BiCountry;
import com.techdisqus.one2one.unidirectional.model.Employee;
import org.junit.Test;

import java.util.List;

public class EmployeeHqlDaoBiDirectionalHqlTest {

    private static final EmployeeHqlDao EMPLOYEE_HQL_DAO = new EmployeeHqlDao();

    @Test
    public void testCreateEmployee(){
        BiCountry biCountry = new BiCountry().setName("IND");


        BiAddress biAddress = new BiAddress().setCityName("BLR").setStateName("KA")
                .setStreetName("NAGARBAVI").setZipCode("12344").setBiCountry(biCountry);
        BiEmployee biEmployee = new BiEmployee().setName("shiva").setSalary(123.32)
                .setBiAddress(biAddress);

        EMPLOYEE_HQL_DAO.save(biEmployee);

    }


    @Test
    public void testgetEmployeeByZipCodeUsingHql(){
        List<Employee> employees = EMPLOYEE_HQL_DAO.getEmployeeByZipCodeUsingHql("12344");
        System.out.println(employees);
    }



}
