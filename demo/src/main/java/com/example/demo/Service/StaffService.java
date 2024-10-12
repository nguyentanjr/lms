package com.example.demo.Service;

import com.example.demo.Model.Staff;
import com.example.demo.Respository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;


}
