package com.techdisqus.one2one.bidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ADDRESS")
public class Address implements Serializable {

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

    public int getId() {
        return id;
    }

    public Address setId(int id) {
        this.id = id;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public Address setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public Address setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Address setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", stateName='" + stateName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                '}';
    }
}
