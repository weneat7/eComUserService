package com.example.ecomuserservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Role extends BaseModel{
    private String role;

    public Role(){}

    public Role(String role){
        this.role = role;
    }
}
