package org.darkend.service;


import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Transactional
public class StudentService {

    @PersistenceContext(unitName = "labb1")
    private EntityManager entityManager;

    @Inject
    private Validator validator;

    public StudentService() {
    }

    public StudentService(EntityManager entityManager, Validator validator) {
        this.entityManager = entityManager;
        this.validator = validator;
    }

    public Student add(Student student) {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);
        if (violations.size() > 0)

            throw new BadRequestException("Provided student is not valid");
        entityManager.persist(student);
        return student;
    }

    public Student get(Long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id))
                .orElseThrow(() -> new EntityNotFoundException("No student in DB with ID: " + id));
    }

    public Student update(Long id, Student student) {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        if (violations.size() > 0)
            throw new BadRequestException("Provided student is not valid");
        try {
            if (!Objects.equals(id, student.getId())) {
                throw new IllegalActionException("Provided student IDs does not match");
            }
            get(student.getId());
        } catch (NullPointerException e) {
            throw new EntityNotFoundException("No student in DB to update with ID: " + student.getId());
        }
        return entityManager.merge(student);
    }

    public Student patch(Long id, Student student) {
        Student studentToPatch = get(id);

        validStudent(student);
        if (student.getFirstName() != null)
            studentToPatch.setFirstName(student.getFirstName());
        if (student.getLastName() != null)
            studentToPatch.setLastName(student.getLastName());
        if (student.getPhoneNumber() != null)
            studentToPatch.setPhoneNumber(student.getPhoneNumber());
        if (student.getEmail() != null)
            studentToPatch.setEmail(student.getEmail());

        return studentToPatch;
    }

    public Student remove(Student student) {
        get(student.getId());
        entityManager.remove(student);
        return student;
    }

    public Student remove(Long id) {
        Student studentToRemove = get(id);
        remove(studentToRemove);
        return studentToRemove;
    }

    public List<Student> getAll() {

        return entityManager.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
    }

    public List<Student> getAll(String lastName) {

        return entityManager.createQuery("SELECT s FROM Student s WHERE s.lastName LIKE :lastName",
                        Student.class)
                .setParameter("lastName", "%" + lastName + "%")
                .getResultList();
    }

    private void validStudent(Student student) {
        if (student.getId() == null &&
                student.getFirstName() == null &&
                student.getLastName() == null &&
                student.getPhoneNumber() == null &&
                student.getEmail() == null)

            throw new BadRequestException("Provided student has no valid data");
    }

    private void validateStudent(Student student) {
        Set<ConstraintViolation<Student>> violations = validator.validate(student);

        if (violations.size() > 0)
            throw new BadRequestException("Provided student is not valid");
    }
}
