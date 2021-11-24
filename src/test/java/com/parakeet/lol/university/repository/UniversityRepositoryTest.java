package com.parakeet.lol.university.repository;

import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.State;
import com.parakeet.lol.model.University;
import com.parakeet.lol.repository.UniversityRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class UniversityRepositoryTest {

    @Autowired
    private UniversityRepository universityRepository;

    @Test
    @Order(1)
    void testFindByUniversityId() {
        var university = universityRepository.findById(190L);
        assertFalse(university.isPresent());
    }

    @Test
    @Order(2)
    void testSaveAndFlush() {
        var entity = new University();
        entity.setName("University 1");
        entity.setAddress("Address 1");
        entity.setDescription("Description 1");
        var universityId = universityRepository.saveAndFlush(entity).getId();
        var university = universityRepository.findById(universityId);
        Assertions.assertTrue(university.isPresent());
    }

    @Test
    @Order(3)
    void testStudentIsSubscribed() {
        var currentStudentUniversityId = universityRepository.findIdCurrentStudentUniversity(
                123L,
                State.SUBSCRIBED.name()
        );
        Assertions.assertNull(currentStudentUniversityId);
    }

    @Test
    @Order(4)
    void testFindAll() {
        var universityList = universityRepository.findAll();
        Assertions.assertFalse(universityList.isEmpty());
    }

}
