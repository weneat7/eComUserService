package com.example.ecomuserservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@JsonDeserialize
@Getter
@Setter
public class CustomGrantedAuthority implements GrantedAuthority {

    private String authority;

    public CustomGrantedAuthority(){}

    public CustomGrantedAuthority(String value){
        this.authority = value;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
