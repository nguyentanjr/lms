package com.example.demo.Services.ServiceImpls;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;
import com.example.demo.Respository.UserRepository;
import com.example.demo.Services.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService,UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
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

    public List<UserDTO> getAllUsers() {
        List<UserDTO> userListDTO = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for(User user : userList) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userListDTO.add(userDTO);
        }
        return userListDTO;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void removeUser(long userId) {
        userRepository.deleteById(userId);
    }
}
