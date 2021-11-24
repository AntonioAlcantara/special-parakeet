package com.parakeet.lol.student.service;

import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.StudentModifierDto;
import com.parakeet.lol.dto.StudentWriterDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import com.parakeet.lol.exception.StudentExistsException;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.repository.StudentRepository;
import com.parakeet.lol.service.StudentService;
import com.parakeet.lol.service.UniversityService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class StudentServiceTest {

    @Autowired
    private StudentService service;
    @Autowired
    private StudentRepository repository;
    @Autowired
    private UniversityService universityService;

    @Test
    @Order(1)
    void testInsertServiceFail() {
        var writer = new StudentWriterDto();
        writer.setName("Nicolas");
        writer.setLastname("Cage");
        writer.setEmail("cage@mail.com");
        writer.setPassportNumber("ABC123456D");
        writer.setAddress("Grove Street, Los Santos");
        writer.setDateOfBirth(LocalDate.of(1964, 1, 7));
        writer.setUniversityId(90L);
        try {
            var id = service.insert(writer);
        } catch (Exception e) {
            Assertions.assertTrue(
                    e instanceof UniversityNotExistsException || e instanceof StudentExistsException
            );
        }
    }

    @Test
    @Order(2)
    void testInsertServiceRun() throws StudentExistsException, UniversityNotExistsException {
        var writerUniversity = new UniversityWriterDto();
        writerUniversity.setName("University A");
        writerUniversity.setAddress("Address A");
        writerUniversity.setDescription("Description A");
        var universityId = universityService.insert(writerUniversity);

        var writer = new StudentWriterDto();
        writer.setName("Nicolas");
        writer.setLastname("Cage");
        writer.setEmail("cage@mail.com");
        writer.setPassportNumber("ABC123458D");
        writer.setAddress("Grove Street, Los Santos");
        writer.setDateOfBirth(LocalDate.of(1964, 1, 7));
        writer.setUniversityId(universityId);

        var id = service.insert(writer);

        var student = repository.findById(id);

        assertTrue(student.isPresent());


        Assertions.assertEquals("Nicolas", student.get().getName());
    }

    @Test
    @Order(3)
    void testUpdate() {
        var list = service.findAll(Optional.empty(), 0, 1);
        assertFalse(list.isEmpty());

        var modifierDto = new StudentModifierDto();
        modifierDto.setName("Eustaquio");
        modifierDto.setLastname("Cage");
        modifierDto.setEmail("cage@mail.com");
        modifierDto.setPassportNumber("ABC123456D");
        modifierDto.setAddress("Grove Street, Los Santos");
        modifierDto.setDateOfBirth(LocalDate.of(1964, 1, 7));

        var id = list.get(0).getId();
        service.updateUserInfo(id, modifierDto);

        var optionalEntity = repository.findById(id);

        Assertions.assertTrue(optionalEntity.isPresent());

        var entity = optionalEntity.get();
        assertEquals("Eustaquio", entity.getName());
    }

    @Test
    @Order(4)
    void testFindAll() {
        var list = service.findAll(Optional.empty(), 0, 1);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(5)
    void testDeleteStudent() {
        var list = service.findAll(Optional.empty(), 0, 1);
        assertFalse(list.isEmpty());

        var isNotDelete = service.delete(list.get(0).getId());
        assertFalse(isNotDelete);
    }
}
