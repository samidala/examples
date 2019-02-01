package com.techdisqus.one2many.unidirectional.dao;

import com.techdisqus.one2many.unidirectional.model.College;
import com.techdisqus.one2many.unidirectional.model.Student;
import com.techdisqus.one2many.unidirectional.model.StudentAddress;
import com.techdisqus.one2many.unidirectional.model.StudentCountry;
import org.junit.Test;

import java.util.List;
import java.util.Random;

public class CollegeStudentHqlDaoTest {

    private static final CollegeStudentHqlDao COLLEGE_STUDENT_DAO = new CollegeStudentHqlDao();

    static final Random random = new Random(0);
    @Test
    public void testSaveStudent(){

        StudentCountry studentCountryIn = new StudentCountry().setName("India");

        for(int i = 1 ; i<= 2; i ++) {
            studentCountryIn = COLLEGE_STUDENT_DAO.getCountryByNameCriteriaJpa("India");

            College college = COLLEGE_STUDENT_DAO.getCollegeByName("DESC4");

            StudentAddress studentAddressLocal = new StudentAddress().setCityName("BLR")
                    .setStudentCountry(studentCountryIn).setStateName("KA")
                    .setStreetName("NAGBHAVY").setZipCode("1" + random.nextInt());

            StudentAddress studentAddressPermanent = new StudentAddress().setCityName("BLLLARY")
                    .setStudentCountry(studentCountryIn).setStateName("KA")
                    .setStreetName("OPD").setZipCode("1" + random.nextInt());


            Student student = new Student().setName("shiva " + random.nextInt())
                    .setCollege(college).setStudentAddresses(List.of(studentAddressLocal, studentAddressPermanent));

            COLLEGE_STUDENT_DAO.saveStudent(college, student);

        }


    }

    @Test
    public void testfindStudentsByCountryUsingHql(){
        List<Student> students = COLLEGE_STUDENT_DAO.findStudentsByCountryUsingHql("US=K");
        System.out.println(students.size());
    }

    @Test
    public void testfindStudentsByCountryUsingCriteria(){
        List<Student> students = COLLEGE_STUDENT_DAO.findStudentsByCountryUsingCriteria("US=K");
        System.out.println(students);
    }

    @Test
    public void testfindStudentsByCountryUsingCriteriaJpa(){
        List<Student> students = COLLEGE_STUDENT_DAO.findStudentsByCountryUsingCriteriaJpa("US=K");
        System.out.println(students);
    }

    @Test
    public void testfindCollegesWithMoreThanXStudentsUsingHql(){
        List<String> colleges = COLLEGE_STUDENT_DAO.findCollegesWithMoreThanXStudentsUsingHql(1);
        System.out.println(colleges);
    }

    @Test
    public void testfindCollegesWithMoreThanXStudentsUsingHqlV2(){
        List<College> colleges = COLLEGE_STUDENT_DAO.findCollegesWithMoreThanXStudentsUsingHqlV2(2);
        System.out.println(colleges);
    }





   @Test
    public void testfindCollegesWithMoreThanXStudentsUsingCriteriaJpa(){
        List<College> colleges = COLLEGE_STUDENT_DAO.findCollegesWithMoreThanXStudentsUsingCriteriaJpa(4);
        System.out.println(colleges);
    }






}
