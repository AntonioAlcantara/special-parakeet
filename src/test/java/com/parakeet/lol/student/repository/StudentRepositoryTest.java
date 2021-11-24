package com.parakeet.lol.student.repository;

import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.State;
import com.parakeet.lol.model.Student;
import com.parakeet.lol.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Order(1)
    void testFindByStudentId() {
        var student = studentRepository.findById(56L);
        assertFalse(student.isPresent());
    }

    @Test
    @Order(2)
    void testSaveAndFlush() {
        var entity = new Student();
        entity.setName("Nicolas");
        entity.setLastname("Cage");
        entity.setEmail("cage@mail.com");
        entity.setPassportNumber("ABC123456D");
        entity.setAddress("Grove Street, Los Santos");
        entity.setDateOfBirth(LocalDate.of(1964, 1, 7));
        var studentId = studentRepository.saveAndFlush(entity).getId();
        var student = studentRepository.findById(studentId);
        Assertions.assertTrue(student.isPresent());
    }

    @Test
    @Order(3)
    void testStudentIsSubscribed() {
        var result = studentRepository.existsByIdAndUniversityIdAndStateAndEndDateIsNull(
                1L,
                134L,
                State.SUBSCRIBED.name()
        );
        Assertions.assertFalse(result);
    }

    @Test
    @Order(4)
    void testFindAll() {
        var studentList = studentRepository.findAll();
        assertFalse(studentList.isEmpty());
    }
}
