package com.test.agingcarev01.Classe;

public class TraitementClasse {
    private int id;
    private String type;
    private String nom;
    private Float dose;
    private String unite;

    private String typeRepetition;
    private String joursRepetition;
    private String dateDebutTaitement;
    private String dateFinTaitement;

    private int nbrTemps;
    private String temp1;
    private String temp2;
    private String temp3;
    private String temp4;
    private String temp5;
    private String temp6;
    private int idResident;

    public TraitementClasse() {
    }

    public TraitementClasse(int id, String type, String nom,
                            Float dose, String unite, String typeRepetition,
                            String joursRepetition, String dateDebutTaitement,
                            String dateFinTaitement, int nbrTemps, String temp1,
                            String temp2, String temp3, String temp4, String temp5,
                            String temp6, int idResident) {
        this.id = id;
        this.type = type;
        this.nom = nom;
        this.dose = dose;
        this.unite = unite;
        this.typeRepetition = typeRepetition;
        this.joursRepetition = joursRepetition;
        this.dateDebutTaitement = dateDebutTaitement;
        this.dateFinTaitement = dateFinTaitement;
        this.nbrTemps = nbrTemps;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.temp4 = temp4;
        this.temp5 = temp5;
        this.temp6 = temp6;
        this.idResident = idResident;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Float getDose() {
        return dose;
    }

    public void setDose(Float dose) {
        this.dose = dose;
    }

    public String getTypeRepetition() {
        return typeRepetition;
    }

    public void setTypeRepetition(String typeRepetition) {
        this.typeRepetition = typeRepetition;
    }

    public String getJoursRepetition() {
        return joursRepetition;
    }

    public void setJoursRepetition(String joursRepetition) {
        this.joursRepetition = joursRepetition;
    }

    public String getDateDebutTaitement() {
        return dateDebutTaitement;
    }

    public void setDateDebutTaitement(String dateDebutTaitement) {
        this.dateDebutTaitement = dateDebutTaitement;
    }

    public String getDateFinTaitement() {
        return dateFinTaitement;
    }

    public void setDateFinTaitement(String dateFinTaitement) {
        this.dateFinTaitement = dateFinTaitement;
    }

    public int getNbrTemps() {
        return nbrTemps;
    }

    public void setNbrTemps(int nbrTemps) {
        this.nbrTemps = nbrTemps;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    public String getTemp5() {
        return temp5;
    }

    public void setTemp5(String temp5) {
        this.temp5 = temp5;
    }

    public String getTemp6() {
        return temp6;
    }

    public void setTemp6(String temp6) {
        this.temp6 = temp6;
    }

    public int getIdResident() {
        return idResident;
    }

    public void setIdResident(int idResident) {
        this.idResident = idResident;
    }
}
