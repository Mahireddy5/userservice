package org.example.userservice.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.userservice.models.Token;
import org.example.userservice.models.User;
import org.example.userservice.repositories.TokenRepository;
import org.example.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenRepository tokenRepository;
    public User signUp(String name , String email, String password)
    {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setHashedpassword(bCryptPasswordEncoder.encode(password));
        User user = userRepository.save(u);
        return user;
    }
    public Token login(String email,String password)
    {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
            return null;
        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedpassword()))
            //Reality throw password not match exception
            return null;
        LocalDate today = LocalDate.now();
        // Add 30 days to today's date
        LocalDate futureLocalDate = today.plusDays(30);
        // Convert LocalDate to Date
        Date date = java.sql.Date.valueOf(futureLocalDate);
        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(date);
        token.setValue(RandomStringUtils.randomAlphabetic(128));
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }
    public void logout(String token)
    {
        Optional<Token> token1 = tokenRepository.findByValueAndDeleted(token,false);
        if(token1.isEmpty())
            //throw TokenNotExistsOrAlreadyExpiredException
            return ;
        Token tkn = token1.get();
        tkn.setDeleted(true);
        tokenRepository.save(tkn);
        return ;



    }
}
