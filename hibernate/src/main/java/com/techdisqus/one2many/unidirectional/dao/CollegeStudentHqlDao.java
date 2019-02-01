package com.techdisqus.one2many.unidirectional.dao;

import com.techdisqus.HibernateUtil;
import com.techdisqus.one2many.unidirectional.model.College;
import com.techdisqus.one2many.unidirectional.model.Student;
import com.techdisqus.one2many.unidirectional.model.StudentAddress;
import com.techdisqus.one2many.unidirectional.model.StudentCountry;
import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.sql.JoinType;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

public class CollegeStudentHqlDao {

    public void saveStudent(College college,Student student){

        Session session = getSession();

        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(college);
        student.setCollege(college);
            session.saveOrUpdate(student);
            session.saveOrUpdate(student);
            session.saveOrUpdate(college);
        transaction.commit();




    }

    public College getCollegeByName(String name){
        String hql = "from College where name = :name";
        return getSession().createQuery(hql,College.class).setParameter("name",name).uniqueResultOptional().orElse(new College().setName(name));
    }

    public StudentCountry getCountryByName(String name){

        Session session = getSession();
        Criteria criteria = session.createCriteria(StudentCountry.class);
        criteria.add(Restrictions.eq("name",name));
        return (StudentCountry) criteria.uniqueResult();


    }

    public StudentCountry getCountryByNameCriteriaJpa(String name){

        Session session = getSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<StudentCountry> studentCountryCriteriaQuery =  criteriaBuilder.createQuery(StudentCountry.class);
        Root<StudentCountry> studentCountryRoot = studentCountryCriteriaQuery.from(StudentCountry.class);
        studentCountryCriteriaQuery.where(criteriaBuilder.equal(studentCountryRoot.get("name"),name));
        return session.createQuery(studentCountryCriteriaQuery).uniqueResultOptional().orElse(new StudentCountry().setName(name));


    }

    public List<Student> findStudentsByCountryUsingHql(String name){
        String hql = "from Student s join fetch s.college join fetch s.studentAddresses a join fetch a.studentCountry c where c.name = :name";
        return getSession().createQuery(hql,Student.class).setParameter("name",name).list();
    }

    public List<Student> findStudentsByCountryUsingCriteria(String name){
       Session session = getSession();
       Criteria criteria = session.createCriteria(Student.class,"s")
               .createAlias("s.studentAddresses","a", JoinType.INNER_JOIN).createAlias("a.studentCountry","c", JoinType.INNER_JOIN)
               .add(Restrictions.eq("c.name",name) );
        return criteria.list();
    }



    public List<Student> findStudentsByCountryUsingCriteriaJpa(String name){
        Session session = getSession();
        CriteriaBuilder cb =session.getCriteriaBuilder();

        CriteriaQuery<Student> studentCriteriaQuery = cb.createQuery(Student.class);

        Root<Student> studentRoot = studentCriteriaQuery.from(Student.class);
        studentCriteriaQuery.select(studentRoot);

        Join<List<StudentAddress>,Student> studentAddressStudentJoin = studentRoot.joinList("studentAddresses", javax.persistence.criteria.JoinType.INNER);
        //studentRoot.fetch("studentAddresses", javax.persistence.criteria.JoinType.INNER);
        studentRoot.fetch("college", javax.persistence.criteria.JoinType.INNER);

        studentCriteriaQuery.where(cb.equal(studentAddressStudentJoin.get("studentCountry").get("name"),name));

        return session.createQuery(studentCriteriaQuery).getResultList();
    }


    public List<String> findCollegesWithMoreThanXStudentsUsingHql(long x){
        String hql = "select c.name from Student s join s.college c group by c.name having count(c.name) > :cnt";
        Query<String> collegeTypedQuery = getSession().createQuery(hql,String.class).setParameter("cnt",x);


        return collegeTypedQuery.getResultList();
    }

    public List<College> findCollegesWithMoreThanXStudentsUsingHqlV2(long x){
        String hql = " from College c where (select count(s.college) from Student s join s.college c1 where c.id = c1.id group by s.college) > :cnt";
        //String hql = " from College c where (select count(s.college) from Student s  where s.college.id = c.id group by s.college) > :cnt"; // this will make cross join
        Query<College> collegeTypedQuery = getSession().createQuery(hql,College.class).setParameter("cnt",x);


        return collegeTypedQuery.getResultList();
    }

    public List<College> findCollegesWithMoreThanXStudentsUsingCriteriaJpa(long x){

        Session session = getSession();



        CriteriaBuilder cb = session.getCriteriaBuilder();

        String hql = " from College c where (select count(s.college) from Student s join s.college c1 where c.id = c1.id group by s.college) > :cnt";


        CriteriaQuery<College> collegeCriteriaQuery = cb.createQuery(College.class);

        Root<College> collegeRoot = collegeCriteriaQuery.from(College.class);

        collegeCriteriaQuery.select(collegeRoot);
        //collegeRoot.alias("outer");


        Subquery<Long> subquery = collegeCriteriaQuery.subquery(Long.class);
        Root<Student> studentRoot = subquery.from(Student.class);
        subquery.select(cb.count(studentRoot.get("college")));
        Join<College,Student> studentCollegeJoin = studentRoot.join("college");

        subquery.where(cb.equal(collegeRoot.get("id"),studentCollegeJoin.get("id") ));

        subquery.correlate(collegeRoot);
        subquery.groupBy(studentCollegeJoin.get("id"));

        collegeCriteriaQuery.where(cb.greaterThanOrEqualTo(subquery,x));


        return session.createQuery(collegeCriteriaQuery).getResultList();

    }

   

    protected Session getSession(){
        return HibernateUtil.buildSessionFactory().openSession();
    }

}
