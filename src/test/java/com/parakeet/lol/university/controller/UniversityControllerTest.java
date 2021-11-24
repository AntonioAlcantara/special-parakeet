package com.parakeet.lol.university.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parakeet.lol.controller.UniversityController;
import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.UniversityModifierDto;
import com.parakeet.lol.dto.UniversityWriterDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class UniversityControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private UniversityController universityController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(universityController).build();
    }

    @Test
    @Order(1)
    void testInsert() throws Exception {
        var bodyUniversity = new UniversityWriterDto();
        bodyUniversity.setName("University of Vienna");
        bodyUniversity.setDescription("The University was founded on 12 March 1365 by Rudolf IV, Duke of Austria");
        bodyUniversity.setAddress("Universitätsring 1, 1010 Wien, Austria");

        mockMvc.perform(
                post("/university/insert").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bodyUniversity))
        ).andExpect(status().is(200));
    }

    @Test
    @Order(2)
    void testFindAll() throws Exception {
        mockMvc.perform(get("/university/findAll")).andExpect(status().is(200));
    }

    @Test
    @Order(3)
    void testUpdateUniversityBasicInfo() throws Exception {
        var body = new UniversityModifierDto();
        body.setName("University of Murcia");
        body.setDescription("We love rice Murciano");
        body.setAddress("Alcantarilla 1, 1010 Murcia, Spain");

        mockMvc.perform(
                put("/university/update/{universityId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().is(200));
    }

    @Test
    @Order(4)
    void testDeleteUniversity() throws Exception {
        mockMvc.perform(
                delete("/university/delete/{studentId}", 1L)
        ).andExpect(status().is(200));
    }
}
