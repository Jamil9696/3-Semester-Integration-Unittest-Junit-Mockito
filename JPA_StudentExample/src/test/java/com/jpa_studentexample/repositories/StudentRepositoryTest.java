package com.jpa_studentexample.repositories;


import com.jpa_studentexample.entities.Student;
import com.jpa_studentexample.repositories.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



/*
    Normally, we don't need to test JPA Methods.
    They have already been tested
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository sRepo;

    @AfterEach
    void tearDown(){
        sRepo.deleteAll();
    }

    @Test
    void checkIfStudentExists(){
        // given
        final int matrikelnr = 123456;
        Student s = new Student(matrikelnr, "test", 21, LocalDate.of(2020,5,10));
        sRepo.save(s);

        // when
        boolean expected = sRepo.existsById(matrikelnr);
        System.out.println(expected + " value ");
        // then
        assertThat(expected).isTrue();


    }

    @Test
    void checkIfStudentDoesNotExist(){
        // given
        final int matrikelnr = 123456;
        // when
        boolean expected = sRepo.existsById(123456);
        System.out.println(expected + " value ");
        // then
        assertThat(expected).isFalse();
    }


}