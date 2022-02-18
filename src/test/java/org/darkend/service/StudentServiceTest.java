package org.darkend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import org.darkend.entity.Student;
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

}