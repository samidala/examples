package com.techdisqus.one2one.bidirectional.dao;

import com.techdisqus.one2one.bidirectional.model.BiAddress;
import com.techdisqus.one2one.bidirectional.model.BiCountry;
import com.techdisqus.one2one.bidirectional.model.BiEmployee;
import com.techdisqus.one2one.unidirectional.model.Employee;
import org.junit.Test;

import java.util.List;

public class EmployeeHqlDaoBiDirectionalCriteriaTest {


    private EmployeeCriteriaDao employeeCriteriaDao = new EmployeeCriteriaDao();

    public void testCreateEmployee(){
        BiCountry biCountry = new BiCountry().setName("IND");


        BiAddress biAddress = new BiAddress().setCityName("BLR").setStateName("KA")
                .setStreetName("NAGARBAVI").setZipCode("12344").setBiCountry(biCountry);
        BiEmployee biEmployee = new BiEmployee().setName("shiva").setSalary(123.32)
                .setBiAddress(biAddress);

        employeeCriteriaDao.save(biEmployee);

    }


    @Test
    public void testgetEmployeeByZipCodeUsingCriteria(){
        List<Employee> employees = employeeCriteriaDao.getEmployeeByZipCodeUsingCriteria("12344");
        System.out.println(employees);
    }

}
