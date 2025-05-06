package Entites;

import java.util.Date;


public class Revision {
    private int id_revision;
    private Date dateRevision;
    private String libelle;
    private int id_MODELE;
    private int id_AVION;
    private int Id_PERSONNEL;


    public Revision() {
    }

    public Revision(int id_revision, Date dateRevision, String libelle, int id_MODELE, int id_AVION, int Id_PERSONNEL) {
        this.id_revision = id_revision;
        this.dateRevision = dateRevision;
        this.libelle = libelle;
        this.id_MODELE = id_MODELE;
        this.id_AVION = id_AVION;
        this.Id_PERSONNEL = Id_PERSONNEL;
    }

    public int getId() {
        return id_revision;
    }

    public void setId(int id) {
        this.id_revision = id;
    }

    public Date getDateR() {
        return dateRevision;
    }

    public void setDateR(Date dateR) {
        this.dateRevision = dateR;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getId_MODELE() {
        return id_MODELE;
    }

    public void setId_MODELE(int id_MODELE) {
        this.id_MODELE = id_MODELE;
    }

    public int getId_AVION() {
        return id_AVION;
    }

    public void setId_AVION(int id_AVION) {
        this.id_AVION = id_AVION;
    }

    public int getId_PERSONNEL() {
        return Id_PERSONNEL;
    }

    public void setId_PERSONNEL(int id_PERSONNEL) {
        Id_PERSONNEL = id_PERSONNEL;
    }
}

