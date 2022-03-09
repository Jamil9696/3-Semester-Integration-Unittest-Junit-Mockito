package com.jpa_studentexample.controllers;

import com.jpa_studentexample.entities.Student;
import com.jpa_studentexample.repositories.StudentRepository;
import com.jpa_studentexample.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private StudentService studentService;
    // This object will be initialized thanks to @AutoConfigureJsonTesters




    private Student createTestStudent(int matrikelNr){
        return new Student(matrikelNr, "yorim", 26, LocalDate.of(1993,9,9));
    }


    @Test
    public void getStudent() throws Exception {
        // in order to mock the database we need to make some assumptions
        // by using the given method
        // given
        Student testStudent = createTestStudent(239575);

        // Optional empty no records have been found



        try {
            given(studentRepository.findById(testStudent.getId())).willReturn(Optional.of(createTestStudent(239575)));
            given(studentService.getStudent(testStudent.getId())).willReturn(Optional.of(createTestStudent(239575)));

            //when(studentService.getStudent(testStudent.getId())).thenReturn(Optional.of(testStudent));
            mockMvc.perform(get("/getStudent/{id}", testStudent.getId()))
                    .andExpect(status().isOk())
                    .andExpect(
                            jsonPath("$.studentList[0].id").value(testStudent.getId()))
                    .andExpect(
                            jsonPath("$.studentList[0].name").value("yorim"))
                    .andExpect(
                            jsonPath("$.studentList[0].age").value(26))
                    .andExpect(
                            jsonPath("$.studentList[0].birthday").value("1993-09-09"));


        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
