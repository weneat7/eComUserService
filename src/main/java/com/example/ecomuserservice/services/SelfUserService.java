package com.example.ecomuserservice.services;

import com.example.ecomuserservice.exceptions.UserNotFoundException;
import com.example.ecomuserservice.exceptions.WrongCredentialsException;
import com.example.ecomuserservice.models.Token;
import com.example.ecomuserservice.repositories.TokenRepository;
import com.example.ecomuserservice.models.User;
import com.example.ecomuserservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service("SelfUserService")
public class SelfUserService implements UserService {


    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Autowired
    public SelfUserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRepository userRepository,
                           TokenRepository tokenRepository){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User userSignUp(String name, String userName, String eMail, String password) {
        User user = new User();
        user.setName(name);
        user.setUserName(userName);
        user.setEmail(eMail);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public Token userLogIn(String userName, String password)throws UserNotFoundException,WrongCredentialsException {
        Optional<User> optionalUser = userRepository.findByUserName(userName);

        if(optionalUser.isEmpty())
            throw new UserNotFoundException("User doesn't exist");

        String hashedPassword = optionalUser.get().getHashedPassword();

        if(!bCryptPasswordEncoder.matches(password,hashedPassword)){
            throw new WrongCredentialsException("Wrong Credentials");
        }

        Token token = getToken(optionalUser.get());
        return tokenRepository.save(token);

    }

    @Override
    public boolean userLogOut(String tokenValue) {
        Optional<Token> tokenOptional = tokenRepository.findByValue(tokenValue);
        if(tokenOptional.isEmpty())
            return false;

        Token token = tokenOptional.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        return true;
    }


    private static Token getToken(User user) {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return token;
    }
}
