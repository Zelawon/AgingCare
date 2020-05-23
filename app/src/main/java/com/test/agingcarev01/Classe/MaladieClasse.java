package com.test.agingcarev01.Classe;

public class MaladieClasse {
    private int idMaladie;
    private String nomMaladie;

    public MaladieClasse() {
    }

    public MaladieClasse(int idMaladie, String nomMaladie) {
        this.idMaladie = idMaladie;
        this.nomMaladie = nomMaladie;
    }

    public int getIdMaladie() {
        return idMaladie;
    }

    public void setIdMaladie(int idMaladie) {
        this.idMaladie = idMaladie;
    }

    public String getNomMaladie() {
        return nomMaladie;
    }

    public void setNomMaladie(String nomMaladie) {
        this.nomMaladie = nomMaladie;
    }
}
