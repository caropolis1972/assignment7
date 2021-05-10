package com.meritamerica.assignment7.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;
    private boolean active;
    private String roles;


    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private AccountHolder accountHolder;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.active = true;
        this.roles = "ROLE_USER";
    }

    // Default constructor.
    public User() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public AccountHolder getAccountHolder() {
        return this.accountHolder;
    }

    public void setAccountHolder(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }
}
