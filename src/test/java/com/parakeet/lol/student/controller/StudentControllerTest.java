package com.parakeet.lol.student.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.parakeet.lol.controller.StudentController;
import com.parakeet.lol.controller.UniversityController;
import com.parakeet.lol.datasource.PostgresTestContainer;
import com.parakeet.lol.dto.StudentModifierDto;
import com.parakeet.lol.dto.StudentWriterDto;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(initializers = {PostgresTestContainer.Initializer.class})
class StudentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private StudentController studentController;
    @Autowired
    private UniversityController universityController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(studentController, universityController).build();
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

        var body = new StudentWriterDto();
        body.setName("Antonio");
        body.setLastname("Alcántara");
        body.setEmail("antonio@mail.com");
        body.setPassportNumber("ABC123456D");
        body.setAddress("Grove Street, Los Santos");
        body.setDateOfBirth(LocalDate.of(1997, 8, 28));
        body.setUniversityId(1L);

        mockMvc.perform(
                post("/student/insert").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().is(200));
    }

    @Test
    @Order(2)
    void testFindAll() throws Exception {
        mockMvc.perform(get("/student/findAll")).andExpect(status().is(200));
    }

    @Test
    @Order(3)
    void testTransferStudent() throws Exception {
        var bodyUniversity = new UniversityWriterDto();
        bodyUniversity.setName("University of Mallorca");
        bodyUniversity.setDescription("We love beer");
        bodyUniversity.setAddress("Palma 1, 1010 Mallorca, Spain");

        mockMvc.perform(
                post("/university/insert").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(bodyUniversity))
        ).andExpect(status().is(200));

        mockMvc.perform(
                put("/student/{studentId}/transfer/university/{universityId}", 1L, 2L)
        ).andExpect(status().is(200));
    }

    @Test
    @Order(4)
    void testUpdateStudentBasicInfo() throws Exception {
        var body = new StudentModifierDto();
        body.setName("Nicolas");
        body.setLastname("Cage");
        body.setEmail("cage@mail.com");
        body.setPassportNumber("ABC123456D");
        body.setAddress("Grove Street, Los Santos");
        body.setDateOfBirth(LocalDate.of(1964, 1, 7));

        mockMvc.perform(
                put("/student/info/{studentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(body))
        ).andExpect(status().is(200));
    }

    @Test
    @Order(5)
    void testStudentCollegeHistory() throws Exception {
        mockMvc.perform(get("/student/find/history/{studentId}", 1L))
                .andExpect(status().is(200));
    }

    @Test
    @Order(6)
    void testDeleteStudent() throws Exception {
        mockMvc.perform(
                delete("/student/delete/{studentId}", 1L)
        ).andExpect(status().is(200));
    }

}
