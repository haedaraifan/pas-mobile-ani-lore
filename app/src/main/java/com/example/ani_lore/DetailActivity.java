package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ani_lore.api.jikan.JikanApiClient;
import com.example.ani_lore.api.jikan.JikanApiService;
import com.example.ani_lore.api.jikan.response.Data;
import com.example.ani_lore.api.jikan.response.DataItem;
import com.example.ani_lore.api.jikan.response.JikanDetailResponseBody;
import com.example.ani_lore.api.jikan.response.JikanResponseBody;
import com.example.ani_lore.databinding.ActivityDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view  = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        String animeId = intent.getStringExtra("anime_id");

        getDetailAnime(animeId);
    }

    private void getDetailAnime(String animeId) {
        progressDialog = new ProgressDialog(DetailActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        Call<JikanDetailResponseBody> call = service.getAnimeById(animeId);
        call.enqueue(new Callback<JikanDetailResponseBody>() {
            @Override
            public void onResponse(Call<JikanDetailResponseBody> call, Response<JikanDetailResponseBody> response) {
                progressDialog.dismiss();

                Data data = response.body().getData();

                setDataUI(data);
            }

            @Override
            public void onFailure(Call<JikanDetailResponseBody> call, Throwable t) {

            }
        });
    }

    private void setDataUI(Data data) {
        String title = data.getTitle();
        String titleJp = data.getTitleJapanese();
        String imageUrl = data.getImages().getJpg().getLargeImageUrl();
        String releaseYear = String.valueOf(data.getYear());
        String score = String.valueOf(data.getScore());
        String episodes = String.valueOf(data.getEpisodes());
        String synopsis = data.getSynopsis();


        binding.tvAnimeTitle.setText(title);
        binding.tvAnimeTitleJp.setText(titleJp);
        binding.tvAnimeReleaseYear.setText(releaseYear);
        binding.tvAnimeRating.setText(score);
        binding.tvAnimeTotalEpisode.setText(episodes);
        binding.tvSinopsis.setText(synopsis);

        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder_error)
                .into(binding.ivAnimePoster);
    }
}