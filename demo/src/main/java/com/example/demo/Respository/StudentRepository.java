package com.example.demo.Respository;

import com.example.demo.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Student deleteById(long studentid);
    @Query("SELECT s FROM Student s WHERE s.studentid=:studentid")
    public Student findByStudentId(long studentid);
}
