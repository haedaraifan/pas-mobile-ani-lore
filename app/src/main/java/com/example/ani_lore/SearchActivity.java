package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ani_lore.adapter.VerticalItemAdapter;
import com.example.ani_lore.api.jikan.JikanApiClient;
import com.example.ani_lore.api.jikan.JikanApiService;
import com.example.ani_lore.api.jikan.response.DataItem;
import com.example.ani_lore.api.jikan.response.JikanResponseBody;
import com.example.ani_lore.api.jikan.response.ProducersItem;
import com.example.ani_lore.databinding.ActivitySearchBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SearchView searchView = binding.searchView;
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchedAnime(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(SearchActivity.this, MainActivity.class));
                    finish();
                    return true;
                case R.id.search:
                    return true;
                case R.id.profile:
                    startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void getSearchedAnime(String query) {
        progressDialog = new ProgressDialog(SearchActivity.this);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        retrofit2.Call<JikanResponseBody> call = service.getAnimeSearch(query);
        call.enqueue(new Callback<JikanResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<JikanResponseBody> call, Response<JikanResponseBody> response) {
                List<DataItem> dataItems = response.body().getData();
                VerticalItemAdapter adapter = new VerticalItemAdapter(dataItems);
                binding.rvVerticalItem.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JikanResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SearchActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}