package com.test.agingcarev01.Classe;

public class TauxGlycemiqueClasse {
    private Float tauxGlyceRes;
    private String dateTauxGlyceRes;
    private String noteTauxGlyceRes;
    private String mesureTauxGlyceRes;

    public TauxGlycemiqueClasse() {
    }

    public TauxGlycemiqueClasse(Float tauxGlyceRes, String dateTauxGlyceRes, String noteTauxGlyceRes, String mesureTauxGlyceRes) {
        this.tauxGlyceRes = tauxGlyceRes;
        this.dateTauxGlyceRes = dateTauxGlyceRes;
        this.noteTauxGlyceRes = noteTauxGlyceRes;
        this.mesureTauxGlyceRes = mesureTauxGlyceRes;
    }

    public float getTauxGlyceRes() {
        return tauxGlyceRes;
    }

    public void setTauxGlyceRes(Float tauxGlyceRes) {
        this.tauxGlyceRes = tauxGlyceRes;
    }

    public String getDateTauxGlyceRes() {
        return dateTauxGlyceRes;
    }

    public void setDateTauxGlyceRes(String dateTauxGlyceRes) {
        this.dateTauxGlyceRes = dateTauxGlyceRes;
    }

    public String getNoteTauxGlyceRes() {
        return noteTauxGlyceRes;
    }

    public void setNoteTauxGlyceRes(String noteTauxGlyceRes) {
        this.noteTauxGlyceRes = noteTauxGlyceRes;
    }

    public String getMesureTauxGlyceRes() {
        return mesureTauxGlyceRes;
    }

    public void setMesureTauxGlyceRes(String mesureTauxGlyceRes) {
        this.mesureTauxGlyceRes = mesureTauxGlyceRes;
    }
}
