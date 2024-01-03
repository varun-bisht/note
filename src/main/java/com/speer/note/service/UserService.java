package com.speer.note.service;

import com.speer.note.entity.UserInfo;
import com.speer.note.entity.UserInfoDetails;
import com.speer.note.payload.request.RegisterReq;
import com.speer.note.payload.response.RegisterRes;
import com.speer.note.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    // Note: here spring security username refer to email to our entity
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userRepository.findByEmail(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public RegisterRes addUser(RegisterReq registerReq) {
        userRepository.save(UserInfo.builder().name(registerReq.getName()).email(registerReq.getEmail()).roles(registerReq.getRoles()).password(encoder.encode(registerReq.getPassword())).build());
        return RegisterRes.builder().message("User created successfully").build();
    }

}
