package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

//    private static final String BASE_URL = "http://192.168.1.12/TTJ/src/ApiResource/API_TTJ/";
//    private static final String BASE_URL = "http://172.20.10.5/API_TTJ/";
    private static final String BASE_URL = "http://10.0.0.2/PPE3/apirestAvion/";
    public static Retrofit getInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
