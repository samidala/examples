package com.techdisqus.one2many.unidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_COUNTRY")
public class StudentCountry {

    @Id
    @Column(name = "COUNTRY_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryCode;

    @Column(name = "COUNTRY_NAME")
    private String name;

    public int getCountryCode() {
        return countryCode;
    }

    public StudentCountry setCountryCode(int countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public StudentCountry setName(String name) {
        this.name = name;
        return this;
    }
}
