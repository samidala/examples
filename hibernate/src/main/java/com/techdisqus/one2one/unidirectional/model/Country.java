package com.techdisqus.one2one.unidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
public class Country {

    @Id
    @Column(name = "COUNTRY_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryCode;

    @Column(name = "COUNTRY_NAME")
    private String name;

    public int getCountryCode() {
        return countryCode;
    }

    public Country setCountryCode(int countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public Country setName(String name) {
        this.name = name;
        return this;
    }
}
