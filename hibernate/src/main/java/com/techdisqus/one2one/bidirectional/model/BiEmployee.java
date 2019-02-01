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
@Table(name = "EMPLOYEE1")
public class BiEmployee implements Serializable {

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
    private BiAddress biAddress;

    public int getId() {
        return id;
    }

    public BiEmployee setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public BiEmployee setName(String name) {
        this.name = name;
        return this;
    }

    public double getSalary() {
        return salary;
    }

    public BiEmployee setSalary(double salary) {
        this.salary = salary;
        return this;
    }

    public BiAddress getBiAddress() {
        return biAddress;
    }

    public BiEmployee setBiAddress(BiAddress biAddress) {
        this.biAddress = biAddress;
        return this;
    }

    @Override
    public String toString() {
        return "BiEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", biAddress=" + biAddress +
                '}';
    }
}
