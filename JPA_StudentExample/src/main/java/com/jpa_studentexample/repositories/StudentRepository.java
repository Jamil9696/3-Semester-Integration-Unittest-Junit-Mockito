package com.jpa_studentexample.repositories;


import com.jpa_studentexample.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findStudentsByNameIsStartingWith(String name);

}
