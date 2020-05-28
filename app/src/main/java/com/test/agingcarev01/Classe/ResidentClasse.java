package com.test.agingcarev01.Classe;

public class ResidentClasse {
    //1st Constructor
    private int id;
    private String nom;
    private String prenom;
    private String sexeRes;
    private String typeSanguin;
    private String dateNaissanceRes;
    private Float poidsCible;
    private Float tauxGlycemiqueCible;
    private Float tensionArterielleCible;
    //status
    private int statutArchivage;

    public ResidentClasse() {
    }

    public ResidentClasse(int id, String nom, String prenom, String sexeRes, String typeSanguin, String dateNaissanceRes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexeRes = sexeRes;
        this.typeSanguin = typeSanguin;
        this.dateNaissanceRes = dateNaissanceRes;
        this.statutArchivage = 0;
        this.poidsCible = 0f;
        this.tauxGlycemiqueCible=0f;
        this.tensionArterielleCible=0f;
    }

    public Float getPoidsCible() {
        return poidsCible;
    }

    public void setPoidsCible(Float poidsCible) {
        this.poidsCible = poidsCible;
    }

    public Float getTauxGlycemiqueCible() {
        return tauxGlycemiqueCible;
    }

    public void setTauxGlycemiqueCible(Float tauxGlycemiqueCible) {
        this.tauxGlycemiqueCible = tauxGlycemiqueCible;
    }

    public Float getTensionArterielleCible() {
        return tensionArterielleCible;
    }

    public void setTensionArterielleCible(Float tensionArterielleCible) {
        this.tensionArterielleCible = tensionArterielleCible;
    }

    public int getStatutArchivage() {
        return statutArchivage;
    }

    public void setStatutArchivage(int statutArchivage) {
        this.statutArchivage = statutArchivage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSexeRes() {
        return sexeRes;
    }

    public void setSexeRes(String sexeRes) {
        this.sexeRes = sexeRes;
    }

    public String getTypeSanguin() {
        return typeSanguin;
    }

    public void setTypeSanguin(String typeSanguin) {
        this.typeSanguin = typeSanguin;
    }

    public String getDateNaissanceRes() {
        return dateNaissanceRes;
    }

    public void setDateNaissanceRes(String dateNaissanceRes) {
        this.dateNaissanceRes = dateNaissanceRes;
    }

}
