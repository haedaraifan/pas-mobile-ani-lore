package com.example.ani_lore.api.jikan;

import com.example.ani_lore.api.jikan.response.JikanResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JikanApiService {

    @GET("top/anime")
    Call<JikanResponseBody> getTopAnime();

    @GET("seasons/now")
    Call<JikanResponseBody> getSeasonNow();

    @GET("anime")
    Call<JikanResponseBody> getAnimeSearch(@Query("q") String animeTitle);

    @GET("anime/{id}")
    Call<JikanResponseBody> getAnimeById(@Path("id") String animeId);
}
