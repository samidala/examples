package com.techdisqus.one2one.bidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SALARY")
    private double salary;

    @OneToOne(fetch = FetchType.EAGER)
   @PrimaryKeyJoinColumn
    private Address address;

    public int getId() {
        return id;
    }

    public Employee setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public Employee setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public Address getAddress() {
        return address;
    }

    public Employee setAddress(Address address) {
        this.address = address;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", address=" + address +
                '}';
    }
}
