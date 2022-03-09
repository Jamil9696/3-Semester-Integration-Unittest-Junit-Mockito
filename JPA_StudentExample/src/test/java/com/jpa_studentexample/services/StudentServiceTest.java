package com.jpa_studentexample.services;

import com.jpa_studentexample.entities.Student;
import com.jpa_studentexample.exception.CustomException;
import com.jpa_studentexample.repositories.StudentRepository;
import com.jpa_studentexample.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
// in this way we don't need to implement AutoClosable
// functionality anymore. it will do it for us
class StudentServiceTest {

    // we don't want to use Autowired Annotations here. We are only interested in its implementations
    // In addition, we already know that the functions worked because we have tested the repository
    // object. That's why we can use a mock instead of a real repository (performance boost)
    //
    @Mock
    private StudentRepository sRepo;

    @Autowired
    private StudentService underTest;

    //private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp(){
       // autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new StudentService(sRepo);
    }

   /* @AfterEach
     void tearDown() throws Exception {
        autoCloseable.close(); // allows us to close the ressource after each test
    }*/

    private Student createTestStudent(){

        return new Student(123456, "Jamil", 25, LocalDate.of(2020,10,20));
    }

    @Test
    void AvoidSavingInvalidStudent() {
        // given

        Student invalidMatrikelNr = new Student(12345, "Jamil",25, LocalDate.of(2020,10,20));
        Student invalidMatrikelNr2 = new Student(1000000, "Jamil", 25, LocalDate.of(2020, 10,20));

        // when
        Throwable tooSmall = assertThrows(CustomException.class, () -> underTest.createStudent(invalidMatrikelNr));
        assertEquals("invalid Matrikelnr", tooSmall.getMessage());
        Throwable tooLong = assertThrows(CustomException.class, () -> underTest.createStudent(invalidMatrikelNr2));
        assertEquals("invalid Matrikelnr", tooLong.getMessage());


        verify(sRepo, never()).save(any());



        /*ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(sRepo).save(studentCaptor.capture())*/
    }
    @Test
    void avoidSavingExistingStudent() {
        // given
        Student duplicate = createTestStudent();

        //when
        given(sRepo.findById(duplicate.getId())).willReturn(Optional.of(createTestStudent()));
        // Two ways of implementing exception handling are possible
        // 1 -> using lambda
        //Throwable existsAlready = assertThrows(CustomException.class, () -> underTest.createStudent(duplicate));
        //assertEquals("Student exists already", existsAlready.getMessage());

        // 2 -> using lambda and functional programming for more readable code

        assertThatThrownBy(() ->  underTest
                                .createStudent(duplicate))
                                .isInstanceOf(CustomException.class)
                                .hasMessageContaining("Student exists already");


        verify(sRepo).findById(duplicate.getId());
        verify(sRepo, never()).save(any());
    }

    @Test
    void createStudent() throws CustomException {
        // given
        Student validStudent = createTestStudent();

        //when

        // Optional empty no records have been found
        given(sRepo.findById(validStudent.getId())).willReturn(Optional.empty());
        underTest.createStudent(validStudent);

        //then
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(sRepo).save(studentCaptor.capture());

        Student capturedStudent = studentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(validStudent);

    }


    @Test
    void getStudents() {

        //given
        List<Student> input = Arrays.asList(createTestStudent());

        //when
        given(sRepo.findAll()).willReturn(input);
        List<Student> res = underTest.getStudents();

        // then
        assertEquals(1, res.size());
    }


    @Test
    @Disabled
    void lookForStudentsWith() {
    }
}