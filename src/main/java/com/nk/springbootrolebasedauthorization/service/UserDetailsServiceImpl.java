package com.nk.springbootrolebasedauthorization.service;

import com.nk.springbootrolebasedauthorization.dtos.RegisterDto;
import com.nk.springbootrolebasedauthorization.dtos.UserDetailsDto;
import com.nk.springbootrolebasedauthorization.entity.User;
import com.nk.springbootrolebasedauthorization.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserDetailsDto(user);
    }
    public void  registerUser(RegisterDto registerDto){
        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        userRepository.save(registerDto.registerDtoToUser(registerDto));
    }
}