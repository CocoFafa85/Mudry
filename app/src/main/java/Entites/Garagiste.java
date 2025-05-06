package Entites;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Garagiste {

    @SerializedName("Id_PERSONNEL")
    private int Id_PERSONNEL;
    @SerializedName("motdepasse")
    private String motdepasse;
    @SerializedName("identidiant")
    private String identidiant;
    @SerializedName("tel")
    private String tel;

    public Garagiste(){}

    public Garagiste(int Id_PERSONNEL, String identidiant, String tel, String motdepasse) {
        this.Id_PERSONNEL = Id_PERSONNEL;
        this.identidiant = identidiant;
        this.tel = tel;
        this.motdepasse = motdepasse;
    }

    public int getId_PERSONNEL() {
        return Id_PERSONNEL;
    }

    public void setId_PERSONNEL(int id_PERSONNEL) {
        Id_PERSONNEL = id_PERSONNEL;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getIdentidiant() {
        return identidiant;
    }

    public void setIdentidiant(String identidiant) {
        this.identidiant = identidiant;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
