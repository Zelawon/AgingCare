package com.test.agingcarev01.Classe;

import java.util.Date;

public class RendezVousClasse {
    private int idRDV;
    private Date dateRDV;
    private String lieuRDV;
    private String nomRDV;
    private String notesRDV;
    private InfirmierClasse responsableRDV;

    public RendezVousClasse() {
    }

    public RendezVousClasse(int idRDV, Date dateRDV, String lieuRDV, String nomRDV, String notesRDV, InfirmierClasse responsableRDV) {
        this.idRDV = idRDV;
        this.dateRDV = dateRDV;
        this.lieuRDV = lieuRDV;
        this.nomRDV = nomRDV;
        this.notesRDV = notesRDV;
        this.responsableRDV = responsableRDV;
    }

    public int getIdRDV() {
        return idRDV;
    }

    public void setIdRDV(int idRDV) {
        this.idRDV = idRDV;
    }

    public Date getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(Date dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getLieuRDV() {
        return lieuRDV;
    }

    public void setLieuRDV(String lieuRDV) {
        this.lieuRDV = lieuRDV;
    }

    public String getNomRDV() {
        return nomRDV;
    }

    public void setNomRDV(String nomRDV) {
        this.nomRDV = nomRDV;
    }

    public String getNotesRDV() {
        return notesRDV;
    }

    public void setNotesRDV(String notesRDV) {
        this.notesRDV = notesRDV;
    }

    public InfirmierClasse getResponsableRDV() {
        return responsableRDV;
    }

    public void setResponsableRDV(InfirmierClasse responsableRDV) {
        this.responsableRDV = responsableRDV;
    }
}

