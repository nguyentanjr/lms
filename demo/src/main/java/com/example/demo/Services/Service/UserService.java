package com.example.demo.Services.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDetails loadUserByUsername(String username);
    Optional<User> findUserByUserName(String username);

    User findUserById(long id);

    void deleteUserByUserId(long id);
    int countTotalUsers();

    List<UserDTO> getAllUsers();

    void saveUser(User user);

    void removeUser(long userId);
}
