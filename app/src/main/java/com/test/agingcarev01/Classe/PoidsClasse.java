package com.test.agingcarev01.Classe;

public class PoidsClasse {
    private float poidsRes;
    private String datePoidRes;
    private String notePoidRes;

    public PoidsClasse() {
    }

    public PoidsClasse(float poidsRes, String datePoidRes, String notePoidRes) {
        this.poidsRes = poidsRes;
        this.datePoidRes = datePoidRes;
        this.notePoidRes = notePoidRes;
    }

    public String getNotePoidRes() {
        return notePoidRes;
    }

    public void setNotePoidRes(String notePoidRes) {
        this.notePoidRes = notePoidRes;
    }

    public PoidsClasse(float poidsRes, String datePoidRes) {
        this.poidsRes = poidsRes;
        this.datePoidRes = datePoidRes;
    }

    public float getPoidsRes() {
        return poidsRes;
    }

    public void setPoidsRes(float poidsRes) {
        this.poidsRes = poidsRes;
    }

    public String getDatePoidRes() {
        return datePoidRes;
    }

    public void setDatePoidRes(String datePoidRes) {
        this.datePoidRes = datePoidRes;
    }
}

