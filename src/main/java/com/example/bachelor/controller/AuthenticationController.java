package com.example.bachelor.controller;

import com.example.bachelor.entities.user.User;
import com.example.bachelor.security.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthenticationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody User user){
        LOG.info("Request for Authentication from {}", user.getUsername());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
        }catch (Exception e)
        {
            LOG.error("Error during authentication!", e);
            return ResponseEntity.badRequest().body("Invalid Username or Password!");
        }
        return ResponseEntity.ok().body(jwtUtil.generateToken(user.getUsername()));
        //ResponseEntity.ok().body(new ResponseUser(user.getUsername(), jwtUtil.generateToken(user.getUsername())));
    }

    @Resource
    public void setJwtUtil(JwtUtil jwtUtil){this.jwtUtil = jwtUtil;}

    @Resource
    public void setAuthenticationManager(AuthenticationManager authenticationManager){
        this.authenticationManager=authenticationManager;
    }
}
