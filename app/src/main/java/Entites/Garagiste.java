package Entites;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Garagiste extends Personnel {

    @SerializedName("motdepasse")
    private String motdepasse;
    @SerializedName("mail")
    private String mail;

    public Garagiste(int tel, String motdepasse, String mail) {
        super(tel);
        this.motdepasse = motdepasse;
        this.mail = mail;
    }

    public String getMotdepasse() {
        return motdepasse;
    }
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {this.mail = mail;}

}
