package com.example.demo.Model;
import com.example.demo.Model.Enum.AccountStatus;
import com.example.demo.Model.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;
    private String username;
    private String password;
    private String role;
    private String email;
    @Column(name="full_name")
    private String fullName;
    @Column(name="phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @CreationTimestamp
    @Column(nullable = false)
    private Date registrationDate;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserBook> userBooks = new ArrayList<>();


}
