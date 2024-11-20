package com.example.demo;

import com.example.demo.Model.Student;
import com.example.demo.Respository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void testAddNew() {
        Student student = new Student();
        student.setEmail("Tan@gmail.com");
        student.setName("tan");
        student.setStudentid(0);
        student.setName("TAN");
        Student savedStudent = studentRepository.save(student);
        Assertions.assertThat(savedStudent).isNotNull();
        Assertions.assertThat(savedStudent.getStudentid()).isGreaterThan(0);
        Assertions.assertThat(savedStudent.getName()).isNotEqualTo("AN");
    }

    @Test void testListAll() {
        Iterable<Student> students = studentRepository.findAll();
        for(Student student : students) {
            System.out.println(student);
        }
    }

    @Test void testDelete() {
        long id = 1;
        studentRepository.deleteById(id);
        Assertions.assertThat(studentRepository.findById(id)).isEmpty();
    }
}
