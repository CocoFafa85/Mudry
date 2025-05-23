package com.example.myapplication;

import com.google.gson.JsonObject;

import java.util.List;

import Entites.Revision;
import Entites.Garagiste;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @Headers({"Authorization: cle"})
    @GET("revisions/lire.php")
    Call<List<JsonObject>> getRevisions();

    @Headers({"Authorization: cle"})
    @POST("revisions/creer.php")
    Call<JsonObject> addRevision(@Body RequestBody body);

    @Headers({"Authorization: cle"})
    @POST("garagiste/auth.php")
    Call<JsonObject> connexion(@Body RequestBody body);

    @Headers({"Authorization: cle"})
    @POST("avion/lire_un.php")
    Call<JsonObject> getAvion(@Body RequestBody body);

    @Headers({"Authorization: cle"})
    @GET("avion/lire.php")
    Call<List<JsonObject>> getAvions();
}
