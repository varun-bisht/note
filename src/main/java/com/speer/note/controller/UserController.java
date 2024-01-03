package com.speer.note.controller;

import com.speer.note.payload.request.LoginReq;
import com.speer.note.payload.request.RegisterReq;
import com.speer.note.payload.response.LoginRes;
import com.speer.note.payload.response.RegisterRes;
import com.speer.note.service.UserService;
import com.speer.note.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<RegisterRes> register(@RequestBody RegisterReq registerReq) {
        return new ResponseEntity<>(userService.addUser(registerReq),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
        if (authentication.isAuthenticated()) {
            return new ResponseEntity<>(LoginRes.builder().token(jwtUtil.generateToken(loginReq.getEmail())).build(), HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}
