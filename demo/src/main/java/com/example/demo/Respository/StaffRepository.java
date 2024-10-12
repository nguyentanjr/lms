package com.example.demo.Respository;

import com.example.demo.Model.Book;
import com.example.demo.Model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
