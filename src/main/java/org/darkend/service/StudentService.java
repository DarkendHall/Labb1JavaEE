package org.darkend.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;
import org.eclipse.persistence.exceptions.DatabaseException;

public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public StudentService() {
    }

    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Student add(Student student) {
        try {
            get(student.getId());
        } catch (EntityNotFoundException e) {
            entityManager.persist(student);
            return student;
        }
        throw new IllegalActionException("Student is already in the database");
    }

    public Student get(long Id) {
        try {
            return entityManager.find(Student.class, Id);
        } catch (DatabaseException e) {
            throw new EntityNotFoundException("Couldn't find a student with that Id");
        }
    }
}
