package org.example.userservice.controllers;

import org.example.userservice.dtos.LoginRequestDto;
import org.example.userservice.dtos.LogoutRequestDto;
import org.example.userservice.dtos.SignUpRequestDto;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request)
    {
        String email = request.getEmail();
        String password = request.getPassword();

        return userService.login(email,password);
    }
    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto request)
    {
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        return userService.signUp(name,email,password);
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request)
    {
        userService.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
