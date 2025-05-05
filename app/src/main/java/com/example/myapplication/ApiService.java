package com.example.myapplication;

import java.util.List;

import Entites.Revision;
import Entites.Garagiste;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("revisions/")
    Call<List<Revision>> getRevisions();

    @GET("revision/{id}/")
    Call<Revision> getRevision(@Path("id") int idRevision);

    @POST("ajoutrevision/")
    Call<Void> addRevision(@Body Garagiste revision);


    @GET("garagiste/{email}/{mdp}/")
    Call<Garagiste> connexionGaragiste(@Path("email") String email, @Path("mdp") String mdp);

    @GET("garagiste/{id}/")
    Call<Revision> getGaragiste(@Path("id") int idGaragiste);

}
