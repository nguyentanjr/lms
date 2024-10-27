package com.example.demo.Model;
import com.example.demo.Model.Enum.AccountStatus;
import com.example.demo.Model.Enum.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

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
    private int phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="account_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;
    @CreationTimestamp
    @Column(nullable = false)
    private Date registration_date;

    @ManyToMany
    @JoinTable(
            name="user_book",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="book_id")
    )
    private Set<Book> books = new HashSet<>();

}
