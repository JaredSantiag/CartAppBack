package com.jaredsantiag.backendcartapp.models.dto;

public class UserDTO {
    private Long Id;
    private String username;
    private String email;
    private boolean admin;

    public UserDTO() {
    }

    public UserDTO(Long id, String username, String email, boolean admin) {
        Id = id;
        this.username = username;
        this.email = email;
        this.admin = admin;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
