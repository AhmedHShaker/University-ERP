package com.university.erp.service;

import com.university.erp.model.CustomUserDetails;
import com.university.erp.model.User;
import com.university.erp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = userRepository.findByEmail(username);
        // Converting userDetail to UserDetails
        return userDetail.map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public String addUser(User user) {
        // use this to encode passwords
        user.setPassword(encoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
        userRepository.save(user);
        return "User Added Successfully";
    }

    public User getUser(String email) throws UsernameNotFoundException{
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
