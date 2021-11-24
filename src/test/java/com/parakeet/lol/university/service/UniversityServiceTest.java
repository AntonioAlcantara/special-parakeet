package com.parakeet.lol.university.service;

import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.UniversityModifierDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import com.parakeet.lol.exception.UniversityNotExistsException;
import com.parakeet.lol.repository.UniversityRepository;
import com.parakeet.lol.service.UniversityService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class UniversityServiceTest {

    @Autowired
    private UniversityService universityService;
    @Autowired
    private UniversityRepository universityRepository;

    @Test
    @Order(1)
    void testInsertServiceFail() {
        try {
            universityService.insert(null);
        } catch (Exception e) {
            Assertions.assertEquals("University writer cannot be null", e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testInsertServiceRun() {
        var writer = new UniversityWriterDto();
        writer.setName("University A");
        writer.setAddress("Address A");
        writer.setDescription("Description A");
        var id = universityService.insert(writer);

        var university = universityRepository.findById(id);

        assertTrue(university.isPresent());

        Assertions.assertEquals("University A", university.get().getName());
    }

    @Test
    @Order(3)
    void testUpdateUniversity() throws UniversityNotExistsException {
        var writer = new UniversityWriterDto();
        writer.setName("University A");
        writer.setAddress("Address A");
        writer.setDescription("Description A");
        var id = universityService.insert(writer);

        var name = "University B";
        var address = "Address B";
        var description = "Description B";

        var modifierDto = new UniversityModifierDto();
        modifierDto.setName(name);
        modifierDto.setAddress(address);
        modifierDto.setDescription(description);
        universityService.updateUniversityInfo(id, modifierDto);

        var optionalEntity = universityRepository.findById(id);

        Assertions.assertTrue(optionalEntity.isPresent());

        var entity = optionalEntity.get();
        assertAll(() -> {
            assertEquals(name, entity.getName());
            assertEquals(description, entity.getDescription());
            assertEquals(address, entity.getAddress());
        });
    }

    @Test
    @Order(4)
    void testFindAll() {
        var list = universityService.findAll(0, 10);
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(5)
    void testDeleteUniversity() {
        var universityList = universityRepository.findAll();
        assertFalse(universityList.isEmpty());
        var isNotDelete = universityService.delete(universityList.get(0).getId());
        assertFalse(isNotDelete);
    }
}
