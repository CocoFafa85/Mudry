package Entites;

import com.google.gson.annotations.SerializedName;

public class Personnel {

    private int id;
    @SerializedName("tel")
    private int tel;



    // Constructeur par défaut (obligatoire pour Gson/Retrofit)
    public Personnel() {
    }

    public Personnel(int tel) {
        this.tel = tel;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getTel() {
        return tel;
    }
    public void setTel(int tel) {
        this.tel = tel;
    }

}
