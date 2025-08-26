package com.github.RestApi.controller;

import com.github.RestApi.entity.Customer;
import com.github.RestApi.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
     public ResponseEntity<String> registerUser(Customer userDto) {
         try {
             String hashPwd = passwordEncoder.encode(userDto.getPwd());
             userDto.setPwd(hashPwd);

             Customer save = customerRepository.save(userDto);
             if(save.getId()>0){
                 return ResponseEntity.ok("User registered successfully With given details");
             }else {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering user");
             }

         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
         }

     }


}

