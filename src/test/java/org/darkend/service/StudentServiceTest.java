package org.darkend.service;

import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

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

    @Test
    @DisplayName("Remove should remove Student in DB")
    void remove() {
        studentService.add(student);

        var result = studentService.remove(student);

        assertThat(result).isEqualTo(student);

        assertThatThrownBy(() -> studentService.get(1)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Remove on empty DB")
    void removeEmptyDB() {
        assertThatThrownBy(() -> studentService.remove(student)).isInstanceOf(IllegalActionException.class);
    }

    @Test
    @DisplayName("Patch should update part of student")
    void patch() {

        studentService.add(student);

        Student student2 = new Student().setPhoneNumber("0716548530");

        var result = studentService.patch(1L, student2);

        assertThat(result).isEqualTo(student.setPhoneNumber("0716548530"));
    }

    @Test
    @DisplayName("Remove with ID")
    void removeWithID() {
        studentService.add(student);

        var result = studentService.remove(1L);

        assertThat(result).isEqualTo(student);
        assertThatThrownBy(() -> studentService.get(1));
    }
}