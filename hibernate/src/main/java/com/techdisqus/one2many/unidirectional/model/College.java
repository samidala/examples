package com.techdisqus.one2many.unidirectional.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COLLEGE")
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLEGE_ID")
    private int id;

    @Column(name = "NAME")
    private String name;


    public int getId() {
        return id;
    }

    public College setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public College setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "College{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
