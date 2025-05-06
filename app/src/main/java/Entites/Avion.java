package Entites;


import java.util.Date;

public class Avion {

    private int Id_AVION;
    private String code;
    private int numSerie;
    private int Id_MODELE;
    private String libelle;
    private int nbSiege;


    public Avion() {
    }

    public Avion(int Id_AVION, String code, int numSerie, int Id_MODELE, String libelle, int nbSiege) {
        this.Id_AVION =Id_AVION;
        this.code =code;
        this.numSerie =numSerie;
        this.Id_MODELE =Id_MODELE;
        this.libelle =libelle;
        this.nbSiege =nbSiege;
    }

    public int getId_AVION() {
        return Id_AVION;
    }

    public void setId_AVION(int id_AVION) {
        Id_AVION = id_AVION;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public int getId_MODELE() {
        return Id_MODELE;
    }

    public void setId_MODELE(int id_MODELE) {
        Id_MODELE = id_MODELE;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getNbSiege() {
        return nbSiege;
    }

    public void setNbSiege(int nbSiege) {
        this.nbSiege = nbSiege;
    }
}


