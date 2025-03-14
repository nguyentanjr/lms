package com.example.demo.DTO;

import com.example.demo.Model.Enum.AccountStatus;
import com.example.demo.Model.Enum.Gender;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private LocalDate registrationDate;
    private String role;

    
}
