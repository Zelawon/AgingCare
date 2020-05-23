package com.test.agingcarev01.Classe;

import java.util.List;

public class ResidentClasse {
    //1st Constructor
    private int id;
    private String nom;
    private String prenom;
    private String sexeRes;
    private String typeSanguin;
    private String dateNaissanceRes;
    //Constructor ???
    private Float poid;
    private Float tauxGlycemique;
    private Float tensionArterielle;
    private List<MaladieClasse> maladieClasseList;
    private List<RendezVousClasse> rendezVousClasseList;
    private List<TraitementClasse> traitementClasseList;
    private List<InfirmierClasse> infirmierList;

    public ResidentClasse() {
    }

    public ResidentClasse(int id, String nom, String prenom, String sexeRes, String typeSanguin, String dateNaissanceRes) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexeRes = sexeRes;
        this.typeSanguin = typeSanguin;
        this.dateNaissanceRes = dateNaissanceRes;
    }

    public ResidentClasse(int id, String nom, String prenom, String sexeRes,
                          String typeSanguin, String dateNaissanceRes, Float poid,
                          Float tauxGlycemique, Float tensionArterielle,
                          List<MaladieClasse> maladieClasseList, List<RendezVousClasse> rendezVousClasseList,
                          List<TraitementClasse> traitementClasseList, List<InfirmierClasse> infirmierList) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.sexeRes = sexeRes;
        this.typeSanguin = typeSanguin;
        this.dateNaissanceRes = dateNaissanceRes;
        this.poid = poid;
        this.tauxGlycemique = tauxGlycemique;
        this.tensionArterielle = tensionArterielle;
        this.maladieClasseList = maladieClasseList;
        this.rendezVousClasseList = rendezVousClasseList;
        this.traitementClasseList = traitementClasseList;
        this.infirmierList = infirmierList;
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

    public Float getPoid() {
        return poid;
    }

    public void setPoid(Float poid) {
        this.poid = poid;
    }

    public Float getTauxGlycemique() {
        return tauxGlycemique;
    }

    public void setTauxGlycemique(Float tauxGlycemique) {
        this.tauxGlycemique = tauxGlycemique;
    }

    public Float getTensionArterielle() {
        return tensionArterielle;
    }

    public void setTensionArterielle(Float tensionArterielle) {
        this.tensionArterielle = tensionArterielle;
    }

    public List<MaladieClasse> getMaladieClasseList() {
        return maladieClasseList;
    }

    public void setMaladieClasseList(List<MaladieClasse> maladieClasseList) {
        this.maladieClasseList = maladieClasseList;
    }

    public List<RendezVousClasse> getRendezVousClasseList() {
        return rendezVousClasseList;
    }

    public void setRendezVousClasseList(List<RendezVousClasse> rendezVousClasseList) {
        this.rendezVousClasseList = rendezVousClasseList;
    }

    public List<TraitementClasse> getTraitementClasseList() {
        return traitementClasseList;
    }

    public void setTraitementClasseList(List<TraitementClasse> traitementClasseList) {
        this.traitementClasseList = traitementClasseList;
    }

    public List<InfirmierClasse> getInfirmierList() {
        return infirmierList;
    }

    public void setInfirmierList(List<InfirmierClasse> infirmierList) {
        this.infirmierList = infirmierList;
    }
}
