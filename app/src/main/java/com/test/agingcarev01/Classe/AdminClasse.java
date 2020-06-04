package com.test.agingcarev01.Classe;

public class AdminClasse {
    private String email;
    private String role;

    public AdminClasse() {
    }

    public AdminClasse(String email) {
        this.email = email;
        this.role = "Admin";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }
}
