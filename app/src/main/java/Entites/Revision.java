package Entites;

import java.util.Date;


public class Revision {
    private int id;
    private Date dateR;
    private String libelle;

    public Revision() {
    }

    public Revision(int id, Date dateR, String libelle) {
        this.id = id;
        this.dateR = dateR;
        this.libelle = libelle;
    }

    public Revision(Date dateR, String libelle) {
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public Date getDateR() {
        return dateR;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}

