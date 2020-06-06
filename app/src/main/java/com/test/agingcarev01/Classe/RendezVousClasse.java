package com.test.agingcarev01.Classe;

public class RendezVousClasse {
    private int idRDV;
    private String dateRDV;
    private String timeRDV;
    private String lieuRDV;
    private String nomRDV;
    private String notesRDV;
    private String numTelRDV;
    private int idResident;

    public RendezVousClasse() {
    }

    public RendezVousClasse(int idRDV, String dateRDV, String timeRDV, String lieuRDV, String nomRDV, String notesRDV, String numTelRDV,int idResident) {
        this.idRDV = idRDV;
        this.dateRDV = dateRDV;
        this.timeRDV = timeRDV;
        this.lieuRDV = lieuRDV;
        this.nomRDV = nomRDV;
        this.notesRDV = notesRDV;
        this.numTelRDV = numTelRDV;
        this.idResident = idResident;
    }

    public int getIdResident() {
        return idResident;
    }

    public void setIdResident(int idResident) {
        this.idResident = idResident;
    }

    public int getIdRDV() {
        return idRDV;
    }

    public void setIdRDV(int idRDV) {
        this.idRDV = idRDV;
    }

    public String getDateRDV() {
        return dateRDV;
    }

    public void setDateRDV(String dateRDV) {
        this.dateRDV = dateRDV;
    }

    public String getTimeRDV() {
        return timeRDV;
    }

    public void setTimeRDV(String timeRDV) {
        this.timeRDV = timeRDV;
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

    public String getNumTelRDV() {
        return numTelRDV;
    }

    public void setNumTelRDV(String numTelRDV) {
        this.numTelRDV = numTelRDV;
    }
}

