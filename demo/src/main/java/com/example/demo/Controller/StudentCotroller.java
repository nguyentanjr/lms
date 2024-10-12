package com.example.demo.Controller;

import com.example.demo.Model.Student;
import com.example.demo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentCotroller {
    @Autowired
    StudentService studentService;
    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student studentCreated = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCreated);
    }

}
