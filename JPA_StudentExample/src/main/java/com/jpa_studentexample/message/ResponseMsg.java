package com.jpa_studentexample.message;

import com.jpa_studentexample.entities.Student;

import java.util.ArrayList;
import java.util.List;

public class ResponseMsg {

    private String url;
    private String error = "";
    private String message;
    private List<Student> studentList = new ArrayList<>();

    // =============== constructors ==============
    public ResponseMsg(String message, String url, List<Student> studentList) {
        this.message = message;
        this.url = url;
        this.studentList = studentList;
    }

    public ResponseMsg(String message, String url, String error) {
        this.message = message;
        this.url = url;
        this.error = error;
    }

    public ResponseMsg(String message, String url) {
        this(message, url, List.of());
    }

    // =================== Getters und Setters =========================


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
