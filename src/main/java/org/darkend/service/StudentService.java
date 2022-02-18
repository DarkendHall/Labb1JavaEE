package org.darkend.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.darkend.entity.Student;

public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public StudentService() {
    }

    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Student add(Student student) {
        entityManager.persist(student);
        return student;
    }

    public Student get(long Id) {
        return entityManager.find(Student.class, Id);
    }
}
