package com.example.ecomuserservice.services;

import com.example.ecomuserservice.models.Token;
import com.example.ecomuserservice.models.User;

public interface UserService {
    public User userSignUp(String name, String userName, String eMail, String password );

    public Token userLogIn(String userName, String password);
    public boolean userLogOut(String tokenValue);
}
