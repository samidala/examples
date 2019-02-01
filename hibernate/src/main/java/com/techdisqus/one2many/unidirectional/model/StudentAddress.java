package com.techdisqus.one2many.unidirectional.model;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_ADDRESS")
public class StudentAddress {


    @Id
    @Column(name = "ADDRESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "STREET_NAME")
    private String streetName;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "STATE_NAME")
    private String stateName;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.ALL})
    @JoinColumn(name = "COUNTRY_CODE")
    private StudentCountry studentCountry;

    public int getId() {
        return id;
    }

    public StudentAddress setId(int id) {
        this.id = id;
        return this;
    }

    public String getStreetName() {
        return streetName;
    }

    public StudentAddress setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public StudentAddress setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getStateName() {
        return stateName;
    }

    public StudentAddress setStateName(String stateName) {
        this.stateName = stateName;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public StudentAddress setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public StudentCountry getStudentCountry() {
        return studentCountry;
    }

    public StudentAddress setStudentCountry(StudentCountry studentCountry) {
        this.studentCountry = studentCountry;
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
