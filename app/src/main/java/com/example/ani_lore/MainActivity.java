package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ani_lore.adapter.HorizontalItemAdapter;
import com.example.ani_lore.adapter.VerticalItemAdapter;
import com.example.ani_lore.api.jikan.JikanApiClient;
import com.example.ani_lore.api.jikan.JikanApiService;
import com.example.ani_lore.api.jikan.response.DataItem;
import com.example.ani_lore.api.jikan.response.JikanResponseBody;
import com.example.ani_lore.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        preferences = new Preferences(this);

        binding.rvHorizontalItem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvVerticalItem.setLayoutManager(new GridLayoutManager(this, 2));

        getTopAnime();
        getOnGoingAnime();

//        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                preferences.setSessionLogin(false);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
    }

    private void getTopAnime() {
        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        Call<JikanResponseBody> call = service.getTopAnime();
        call.enqueue(new Callback<JikanResponseBody>() {
            @Override
            public void onResponse(Call<JikanResponseBody> call, Response<JikanResponseBody> response) {

                List<DataItem> dataItems = response.body().getData();
                HorizontalItemAdapter adapter = new HorizontalItemAdapter(dataItems);
                binding.rvHorizontalItem.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JikanResponseBody> call, Throwable t) {

            }
        });
    }

    private void getOnGoingAnime() {
        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        Call<JikanResponseBody> call = service.getSeasonNow();
        call.enqueue(new Callback<JikanResponseBody>() {
            @Override
            public void onResponse(Call<JikanResponseBody> call, Response<JikanResponseBody> response) {

                List<DataItem> dataItems = response.body().getData();
                VerticalItemAdapter adapter = new VerticalItemAdapter(dataItems);
                binding.rvVerticalItem.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JikanResponseBody> call, Throwable t) {

            }
        });
    }
}