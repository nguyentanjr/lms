package com.example.demo.Respository;

import com.example.demo.Model.Book;
import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUserName(String username);

    User findById(long id);

    void deleteById(long id);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    int countTotalUser(String role);

    @Query("SELECT u FROM User u")
    List<User> findAll();


}
