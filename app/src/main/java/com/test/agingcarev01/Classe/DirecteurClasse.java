package com.test.agingcarev01.Classe;

public class DirecteurClasse {
    private String emailDirec;
    private String nomDirec;
    private String prenomDirec;
    private String role;

    public DirecteurClasse() {
    }

    public DirecteurClasse(String emailDirec, String nomDirec, String prenomDirec) {
        this.emailDirec = emailDirec;
        this.nomDirec = nomDirec;
        this.prenomDirec = prenomDirec;
        this.role = "Directeur";
    }

    public String getEmailDirec() {
        return emailDirec;
    }

    public void setEmailDirec(String emailDirec) {
        this.emailDirec = emailDirec;
    }

    public String getNomDirec() {
        return nomDirec;
    }

    public void setNomDirec(String nomDirec) {
        this.nomDirec = nomDirec;
    }

    public String getPrenomDirec() {
        return prenomDirec;
    }

    public void setPrenomDirec(String prenomDirec) {
        this.prenomDirec = prenomDirec;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
