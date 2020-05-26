package com.test.agingcarev01.Classe;

public class DirecteurClasse {
    private String email;
    private String nom;
    private String prenom;
    private String role;
    private String statutEtRole;

    public DirecteurClasse() {
    }

    public DirecteurClasse(String email, String nom, String prenom) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.role = "Directeur";
        this.statutEtRole = "Directeur_0";
    }

    public String getStatutEtRole() {
        return statutEtRole;
    }

    public void setStatutEtRole(String statutEtRole) {
        this.statutEtRole = statutEtRole;
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
}
