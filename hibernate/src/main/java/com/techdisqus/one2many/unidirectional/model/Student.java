package com.techdisqus.one2many.unidirectional.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @Column(name = "STUDENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COLLEGE_ID",referencedColumnName = "COLLEGE_ID")
    private College college;


    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "STUDENT_ID",referencedColumnName = "STUDENT_ID")
    private List<StudentAddress> studentAddresses;

    public int getId() {
        return id;
    }

    public Student setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public College getCollege() {
        return college;
    }

    public Student setCollege(College college) {
        this.college = college;
        return this;
    }

    public List<StudentAddress> getStudentAddresses() {
        return studentAddresses;
    }

    public Student setStudentAddresses(List<StudentAddress> studentAddresses) {
        this.studentAddresses = studentAddresses;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", college=" + college +
                ", studentAddresses=" + studentAddresses +
                '}';
    }

}
