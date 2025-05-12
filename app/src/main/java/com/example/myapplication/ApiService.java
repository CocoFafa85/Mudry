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
import retrofit2.http.Path;

public interface ApiService {
    @Headers({"Authorization: cle"})
    @GET("revisions/lire.php")
    Call<List<JsonObject>> getRevisions();

    @GET("revision/{id}/")
    Call<Revision> getRevision(@Path("id") int idRevision);

    @POST("ajoutrevision/")
    Call<Void> addRevision(@Body Garagiste revision);

    @Headers({"Authorization: cle"})
    @POST("garagiste/auth.php")
    Call<JsonObject> connexion(@Body RequestBody body);

    @GET("garagiste/{id}/")
    Call<Revision> getGaragiste(@Path("id") int idGaragiste);

}
