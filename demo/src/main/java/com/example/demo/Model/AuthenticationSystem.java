package com.example.demo.Model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class AuthenticationSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String loginId;
    private String password;

}
