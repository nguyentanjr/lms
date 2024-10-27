package com.example.demo.Service;

import com.example.demo.Model.Card;
import com.example.demo.Model.CardStatus;
import com.example.demo.Model.Student;
import com.example.demo.Respository.CardRepository;
import com.example.demo.Respository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    CardRepository cardRepository;
    public Student createStudent(Student student) {
        Card card = new Card();
        card.setStudent(student);
        card.setCardStatus(CardStatus.ACTIVATED);
        card.setExpireDateLeft(60);
        student.setCard(card);
        cardRepository.save(card);
        return studentRepository.save(student);
    }
    public Student deleteStudentById(long studentid) {
        return studentRepository.deleteById(studentid);
    }
    public List<Student> listAllStudents() {
        return studentRepository.findAll();
    }

    public void save(Student student) {
        studentRepository.save(student);
    }
    public Student findStudentByStudentId(long studentid) {
        return studentRepository.findByStudentId(studentid);
    }

}
