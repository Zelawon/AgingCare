package com.test.agingcarev01.Classe;

public class SurveillantClasse {
    private String emailSurv;
    private String nomSurv;
    private String prenomSurv;
    private String role="Surviellant";
    private String id_Surv;

    public SurveillantClasse() {
    }

    public SurveillantClasse(String emailSurv, String nomSurv, String prenomSurv, String role, String id_Surv) {
        this.emailSurv = emailSurv;
        this.nomSurv = nomSurv;
        this.prenomSurv = prenomSurv;
        this.role = role;
        this.id_Surv = id_Surv;
    }

    public String getEmailSurv() {
        return emailSurv;
    }

    public void setEmailSurv(String emailSurv) {
        this.emailSurv = emailSurv;
    }

    public String getNomSurv() {
        return nomSurv;
    }

    public void setNomSurv(String nomSurv) {
        this.nomSurv = nomSurv;
    }

    public String getPrenomSurv() {
        return prenomSurv;
    }

    public void setPrenomSurv(String prenomSurv) {
        this.prenomSurv = prenomSurv;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId_Surv() {
        return id_Surv;
    }

    public void setId_Surv(String id_Surv) {
        this.id_Surv = id_Surv;
    }
}
