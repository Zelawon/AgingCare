package com.test.agingcarev01.Classe;

public class TensionArterielleClasse {
    private Float pressionSystolique;
    private Float preessionDiatolique;
    private String brasMesure;
    private String dateTensionArterielle;
    private String noteTensionArterielle;

    public TensionArterielleClasse() {
    }

    public TensionArterielleClasse(Float pressionSystolique, Float preessionDiatolique, String brasMesure, String dateTensionArterielle, String noteTensionArterielle) {
        this.pressionSystolique = pressionSystolique;
        this.preessionDiatolique = preessionDiatolique;
        this.brasMesure = brasMesure;
        this.dateTensionArterielle = dateTensionArterielle;
        this.noteTensionArterielle = noteTensionArterielle;
    }

    public Float getPressionSystolique() {
        return pressionSystolique;
    }

    public void setPressionSystolique(Float pressionSystolique) {
        this.pressionSystolique = pressionSystolique;
    }

    public Float getPreessionDiatolique() {
        return preessionDiatolique;
    }

    public void setPreessionDiatolique(Float preessionDiatolique) {
        this.preessionDiatolique = preessionDiatolique;
    }

    public String getBrasMesure() {
        return brasMesure;
    }

    public void setBrasMesure(String brasMesure) {
        this.brasMesure = brasMesure;
    }

    public String getDateTensionArterielle() {
        return dateTensionArterielle;
    }

    public void setDateTensionArterielle(String dateTensionArterielle) {
        this.dateTensionArterielle = dateTensionArterielle;
    }

    public String getNoteTensionArterielle() {
        return noteTensionArterielle;
    }

    public void setNoteTensionArterielle(String noteTensionArterielle) {
        this.noteTensionArterielle = noteTensionArterielle;
    }
}
