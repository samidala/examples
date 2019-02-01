package com.techdisqus.one2one.bidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS1")
public class BiAddress implements Serializable {

    @Id
    @Column(name = "EMPLOYEE_ID")
    private int id;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "STATE_NAME")
    private String stateName;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @OneToOne(mappedBy = "biAddress")
    private BiEmployee biEmployee;

    @OneToOne
    @JoinColumn(name = "COUNTRY_CODE")
    private BiCountry biCountry;

    public int getId() {
        return id;
    }

    public BiAddress setId(int id) {
        this.id = id;
        return this;
    }


    public String getStreetName() {
        return streetName;
    }

    public BiAddress setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public BiAddress setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public BiAddress setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public BiAddress setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public BiEmployee getBiEmployee() {
        return biEmployee;
    }

    public BiAddress setBiEmployee(BiEmployee biEmployee) {
        this.biEmployee = biEmployee;
        return this;
    }

    public BiCountry getBiCountry() {
        return biCountry;
    }

    public BiAddress setBiCountry(BiCountry biCountry) {
        this.biCountry = biCountry;
        return this;
    }

    @Override
    public String toString() {
        return "BiAddress{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
