package com.test.agingcarev01.Classe;

public class InfirmierClasse {
    private String email;
    private String nom;
    private String prenom;
    private String role;
    private String sexe;

    public InfirmierClasse() {
    }

    public InfirmierClasse(String email, String nom, String prenom, String sexe) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = "Infirmier";
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
}
