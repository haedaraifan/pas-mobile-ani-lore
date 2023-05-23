package com.example.ani_lore.api.jikan;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JikanApiClient {
    public static Retrofit retrofit;
    public static final String BASE_URL = "https://api.jikan.moe/v4/";

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
