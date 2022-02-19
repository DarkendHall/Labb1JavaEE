package org.darkend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StudentServiceTest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    StudentService studentService;
    Student student;

    @BeforeEach
    private void setup() {
        studentService = new StudentService(entityManager);
        student = new Student("Marcus", "Leeman", "test@email.com");
    }

    @Test
    @DisplayName("Add should add")
    void add() {

        var result = studentService.add(student);

        var result1 = studentService.get(1);

        assertThat(result).isEqualTo(student);
        assertThat(result1).isEqualTo(student);
    }

    @Test
    @DisplayName("Get should throw EntityNotFoundException")
    void get() {
        assertThatThrownBy(() -> studentService.get(1)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Add with duplicate student")
    void addDuplicate() {
        studentService.add(student);

        assertThatThrownBy(() -> studentService.add(student)).isInstanceOf(IllegalActionException.class);
    }

    @Test
    @DisplayName("Update should update Student in DB")
    void update() {
        studentService.add(student);

        student.setLastName("Leemann");

        var result = studentService.update(student);
        var result1 = studentService.get(1);

        assertThat(result).isEqualTo(result1);
    }

    @Test
    @DisplayName("Update with no Student in DB")
    void updateEmptyDB() {
        assertThatThrownBy(() -> studentService.update(student)).isInstanceOf(IllegalActionException.class);
    }

}