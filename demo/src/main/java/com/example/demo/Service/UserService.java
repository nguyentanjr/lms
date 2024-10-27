package com.example.demo.Service;

import com.example.demo.Model.User;
import com.example.demo.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUserName(username);
        if(user.isPresent()) {
            User myUser = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(myUser.getUsername())
                    .password(myUser.getPassword())
                    .roles(myUser.getRole())
                    .build();
        } else throw new UsernameNotFoundException("NOT FOUND" + username);
    }

    public Optional<User> findUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public User findUserById(long id) {
        return userRepository.findById(id);
    }

    public void deleteUserByUserId(long id) {
        userRepository.deleteById(id);
    }
    public int countTotalUsers() {
        return userRepository.countTotalUser("USER");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
