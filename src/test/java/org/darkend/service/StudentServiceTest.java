package org.darkend.service;

import org.darkend.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    EntityManager entityManager;
    Validator validator;

    StudentService studentService;
    Student student;

    @BeforeEach
    private void setup() {
        entityManager = mock(EntityManager.class);
        validator = mock(Validator.class);
        studentService = new StudentService(entityManager, validator);
        student = new Student("Marcus", "Leeman", "test@email.com").setId(1L);
    }

    @Test
    @DisplayName("Add should add")
    void add() {
        var result = studentService.add(student);

        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        var result1 = studentService.get(1L);

        assertThat(result).isEqualTo(student);
        assertThat(result1).isEqualTo(student);
    }

    @Test
    @DisplayName("Get should throw EntityNotFoundException")
    void get() {
        assertThatThrownBy(() -> studentService.get(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Update should update Student in DB")
    void update() {
        student.setLastName("Leemann");
        doReturn(student).when(entityManager)
                .merge(student);
        doReturn(student).when(entityManager)
                .find(Student.class, 1L);
        var result = studentService.update(1L, student);
        var result1 = studentService.get(1L);

        assertThat(result).isEqualTo(result1);
    }

    @Test
    @DisplayName("Update with no Student in DB")
    void updateEmptyDB() {
        assertThatThrownBy(() -> studentService.update(1L, student)).isInstanceOf(EntityNotFoundException.class);
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

        assertThatThrownBy(() -> studentService.get(1L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Remove on empty DB")
    void removeEmptyDB() {
        assertThatThrownBy(() -> studentService.remove(student)).isInstanceOf(EntityNotFoundException.class);
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
        assertThatThrownBy(() -> studentService.get(1L));
    }

    @Test
    @DisplayName("GetAll should return all students in DB")
    void getAll() {

        Student student2 = new Student("test", "testersson", "test@test.test").setId(2L);

        TypedQuery<Student> tq = mock(TypedQuery.class);
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(student2);


        when(tq.getResultList()).thenReturn(students);
        when(entityManager.createQuery("SELECT s FROM Student s", Student.class)).thenReturn(tq);

        var result = studentService.getAll();

        assertThat(result).containsExactly(student, student2);
    }

    @Test
    @DisplayName("GetAll should return all students in DB with matching last name")
    void getAllQuery() {

        String lastName = "test";

        Student student2 = new Student("test", "testersson", "test@test.test").setId(2L);

        TypedQuery<Student> tq = mock(TypedQuery.class);
        List<Student> students = new ArrayList<>();
        students.add(student2);


        when(tq.setParameter("lastName", "%" + lastName + "%")).thenReturn(tq);
        when(tq.getResultList()).thenReturn(students);
        when(entityManager.createQuery("SELECT s FROM Student s WHERE s.lastName LIKE :lastName",
                Student.class)).thenReturn(tq);

        var result = studentService.getAll(lastName);

        assertThat(result).containsExactly(student2);
    }
}