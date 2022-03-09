package com.jpa_studentexample.controllers;

import com.jpa_studentexample.exception.CustomException;
import com.jpa_studentexample.message.ResponseMsg;
import com.jpa_studentexample.services.StudentService;
import com.jpa_studentexample.entities.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {


    private final StudentService sService;

    @Autowired
    public StudentController(StudentService sService){
        this.sService = sService;
    }



    @GetMapping(path ="/getAll")
    public ResponseEntity<ResponseMsg> getStudents(HttpServletRequest request){

        try {
            // get all documents from MongoDB database
            return requestSucceeded("Retrieve all  successfully!",
                                            request,
                                            sService.getStudents());
        }catch(Exception e) {
            return requestFailed("Request failed",
                                          request, new CustomException(e.getMessage(),
                                          HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping(path = "/startswith={name}")
    public ResponseEntity<ResponseMsg> lookForStudentsWith(@PathVariable String name, HttpServletRequest request) {

        try {
            String message = "all possible students selected starting with name: " + name;
            return requestSucceeded(message,
                                    request,
                                    sService.lookForStudentsWith(name));
        } catch (CustomException e) {
            return requestFailed("request succeeded but no response",
                                         request,
                                         e);
           }
        }

    @PostMapping(path = "/save-student")
    public ResponseEntity<ResponseMsg> saveStudent(@RequestBody Student s, HttpServletRequest request){

        try{
            sService.createStudent(s);
            String message = "response received: Student with id  = " + s.getId();
            return requestSucceeded(message,
                                    request,
                                    List.of(s));
        }catch(CustomException e){

            return requestFailed("Upload failed",
                                         request,
                                         e);
        }
    }

    @GetMapping("/getStudent/{id}")
    public ResponseEntity<ResponseMsg> getStudent(@PathVariable int id, HttpServletRequest request){

        try {
            Optional<Student> s = sService.getStudent(id);
            String message = "Retrieve all  successfully!";

            List<Student> list = new ArrayList<>();
            s.ifPresent(list::add);

            return requestSucceeded(message,
                                    request,
                                    list);
        }catch(CustomException e){

            return requestFailed("request succeed but no response",
                                          request,
                                          e );
        }




    }


    @GetMapping("/test/{name}")
    public Student test(@PathVariable String name){
       return new Student(1234, name, 23, LocalDate.of(1996,11,12));
    }

    private ResponseEntity<ResponseMsg> requestSucceeded(String message, HttpServletRequest request, List<Student> list){
        return new ResponseEntity<>(
                new ResponseMsg(
                        message,
                        request.getRequestURI(),
                        list),
                HttpStatus.OK);
    }
    
    private ResponseEntity<ResponseMsg> requestFailed(String message,HttpServletRequest request, CustomException e){

        return new ResponseEntity<>(
                new ResponseMsg(
                        message,
                        request.getRequestURI(),
                        e.getMessage()),
                        e.getStatus());
        
    }

}