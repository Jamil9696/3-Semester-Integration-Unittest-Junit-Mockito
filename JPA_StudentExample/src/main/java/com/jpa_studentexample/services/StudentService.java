package com.jpa_studentexample.services;

import com.jpa_studentexample.exception.CustomException;
import com.jpa_studentexample.entities.Student;
import com.jpa_studentexample.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    private final StudentRepository sRepo;


    @Autowired
    public StudentService(StudentRepository sRepo){
        this.sRepo = sRepo;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void createStudent(Student s) throws CustomException {
        if(s.getId() < 100000 || s.getId() > 999999 )
            throw new CustomException("invalid Matrikelnr", HttpStatus.BAD_REQUEST);

        if(getStudent(s.getId()).isPresent())
            throw new CustomException("Student exists already", HttpStatus.FORBIDDEN);

       sRepo.save(s);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Student> getStudents(){
        return sRepo.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<Student> getStudent(int id) throws CustomException {

      Optional<Student> optional = sRepo.findById(id);

      if(optional.isPresent()){
          System.out.println("gefunden");
      }else{
          System.out.println("nicht gefunden");
      }
      return optional;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Student> lookForStudentsWith(String name) throws CustomException {
        List<Student> list = sRepo.findStudentsByNameIsStartingWith(name);

        if(list.isEmpty())
            throw new CustomException("No Students found ", HttpStatus.NOT_FOUND);

        return list;
    }
}
