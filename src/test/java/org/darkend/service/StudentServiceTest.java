package org.darkend.service;

import org.darkend.entity.Student;
import org.darkend.exception.IllegalActionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    EntityManager entityManager;

    StudentService studentService;
    Student student;

    @BeforeEach
    private void setup() {
        entityManager = mock(EntityManager.class);
        studentService = new StudentService(entityManager);
        student = new Student("Marcus", "Leeman", "test@email.com").setId(1L);
    }

    @Test
    @DisplayName("Add should add")
    void add() {
        var result = studentService.add(student);

        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
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
        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        assertThatThrownBy(() -> studentService.add(student)).isInstanceOf(IllegalActionException.class);
    }

    @Test
    @DisplayName("Update should update Student in DB")
    void update() {
        student.setLastName("Leemann");
        doReturn(student).when(entityManager)
                .merge(student);
        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
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
        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        var result = studentService.remove(student);

        assertThat(result).isEqualTo(student);
        doThrow(new EntityNotFoundException()).when(entityManager)
                .find(Student.class, 1L);

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
        Student student2 = new Student().setPhoneNumber("0716548530");

        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        var result = studentService.patch(1L, student2);

        assertThat(result).isEqualTo(student.setPhoneNumber("0716548530"));
    }

    @Test
    @DisplayName("Remove with ID")
    void removeWithID() {
        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        var result = studentService.remove(1L);

        assertThat(result).isEqualTo(student);
        doThrow(new EntityNotFoundException()).when(entityManager)
                .find(Student.class, 1L);
        assertThatThrownBy(() -> studentService.get(1));
    }
}