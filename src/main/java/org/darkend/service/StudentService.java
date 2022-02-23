package org.darkend.service;


import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            entityManager.persist(student);
            return student;
    }

    public Student get(Long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id))
                .orElseThrow(() -> new EntityNotFoundException("No student in DB with ID: " + id));
    }

    public Student update(Long id, Student student) {
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

        validateStudent(student);
        if (student.getId() != null)
            studentToPatch.setId(student.getId());
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
        var students = entityManager.createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();

        if (students.size() == 0)
            throw new EntityNotFoundException("No students in the DB");

        return students;
    }

    public List<Student> getAll(String lastName) {
        var students = entityManager.createQuery("SELECT s FROM Student s WHERE s.lastName LIKE :lastName",
                        Student.class)
                .setParameter("lastName", "%" + lastName + "%")
                .getResultList();

        if (students.size() == 0)
            throw new EntityNotFoundException("No students matched query parameter: " + lastName);

        return students;
    }

    private void validateStudent(Student student) {
        if (student.getId() == null &&
                student.getFirstName() == null &&
                student.getLastName() == null &&
                student.getPhoneNumber() == null &&
                student.getEmail() == null)

            throw new BadRequestException("Provided student has no valid data");
    }
}
