package com.example.demo.DTO;

import com.example.demo.Model.Enum.AccountStatus;
import com.example.demo.Model.Enum.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Getter
@Setter
public class UpdateUserDTO {
    private long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date registrationDate;
    private String role;
}
