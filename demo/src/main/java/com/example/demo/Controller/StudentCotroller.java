package com.example.demo.Controller;

import com.example.demo.Model.Student;
import com.example.demo.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentCotroller {
    @Autowired
    StudentService studentService;
    @GetMapping("/students/student_form")
    public String createStudent(Model model) {
        model.addAttribute("student",new Student());
        return "student_form";
    }
    @PostMapping("/students/student_form/save")
    public String saveStudent(Student student) {
        studentService.save(student);
        return "redirect:/students";
    }
    @GetMapping("/students/editInformation/{studentid}")
    public String editInformation(Model model, @PathVariable long studentid) {
        Student student = studentService.findStudentByStudentId(studentid);
        model.addAttribute("student",student);
        return "update_information";
    }
    @PostMapping("/students/editInformation/save")
    public String updateStudent(Student student) {
        Student updatedStudent = studentService.findStudentByStudentId(student.getStudentid());
        updatedStudent.setName(student.getName());
        updatedStudent.setEmail(student.getEmail());
        studentService.save(updatedStudent);
        return "redirect:/students";
    }
    @GetMapping("/students/deleteStudentById/{studentId}")
    public String deleteStudentById(@PathVariable long studentId) {
        studentService.deleteStudentById(studentId);
        return "redirect:/students";
    }
    @GetMapping("/students")
    public String listOfStudents(Model model) {
        List<Student> studentList = studentService.listAllStudents();
        model.addAttribute("studentLists",studentList);
        return "students";
    }
    @PostMapping("/createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student studentCreated = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentCreated);
    }
}
