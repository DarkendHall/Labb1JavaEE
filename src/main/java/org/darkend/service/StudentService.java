package org.darkend.service;


import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;
import org.eclipse.persistence.exceptions.DatabaseException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class StudentService {

    @PersistenceContext(unitName = "labb1")
    EntityManager entityManager;

    public StudentService() {
    }

    public StudentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Student add(Student student) {
        try {
            get(student.getId());
        } catch (EntityNotFoundException | NullPointerException e) {
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

    public Student update(Student student) {
        try {
            return entityManager.merge(student);
        } catch (DatabaseException e) {
            throw new IllegalActionException("No such Student to update");
        }
    }

    public Student remove(Student student) {
        try {
            entityManager.remove(student);

            return student;
        } catch (DatabaseException e) {
            throw new IllegalActionException("No such Student to remove");
        }
    }
}
