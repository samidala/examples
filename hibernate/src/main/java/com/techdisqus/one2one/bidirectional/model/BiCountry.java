package com.techdisqus.one2one.bidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY1")
public class BiCountry {

    @Id
    @Column(name = "COUNTRY_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryCode;

    @Column(name = "COUNTRY_NAME")
    private String name;

    @OneToOne(mappedBy = "biCountry")
    private BiAddress biAddress;

    public int getCountryCode() {
        return countryCode;
    }

    public BiCountry setCountryCode(int countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getName() {
        return name;
    }

    public BiCountry setName(String name) {
        this.name = name;
        return this;
    }

    public BiAddress getBiAddress() {
        return biAddress;
    }

    public BiCountry setBiAddress(BiAddress biAddress) {
        this.biAddress = biAddress;
        return this;
    }
}
