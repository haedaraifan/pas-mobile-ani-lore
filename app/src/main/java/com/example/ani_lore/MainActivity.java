package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ani_lore.adapter.HorizontalItemAdapter;
import com.example.ani_lore.adapter.VerticalItemAdapter;
import com.example.ani_lore.api.jikan.JikanApiClient;
import com.example.ani_lore.api.jikan.JikanApiService;
import com.example.ani_lore.api.jikan.response.DataItem;
import com.example.ani_lore.api.jikan.response.JikanResponseBody;
import com.example.ani_lore.databinding.ActivityMainBinding;
import com.example.ani_lore.db.AppDatabase;
import com.example.ani_lore.db.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ProgressDialog progressDialog;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-login").allowMainThreadQueries().build();

        List<User> users = db.userDao().getAll();
        if(!users.isEmpty()) {
            binding.nameGreeting.setText(users.get(0).username);
        }

        binding.rvHorizontalItem.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvVerticalItem.setLayoutManager(new GridLayoutManager(this, 2));

        getTopAnime();

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    return true;
                case R.id.search:
                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
                    finish();
                    return true;
                case R.id.profile:
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    finish();
                    return true;
            }
            return false;
        });
    }

    private void getTopAnime() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        Call<JikanResponseBody> call = service.getTopAnime();
        call.enqueue(new Callback<JikanResponseBody>() {
            @Override
            public void onResponse(Call<JikanResponseBody> call, Response<JikanResponseBody> response) {

                List<DataItem> dataItems = response.body().getData();
                HorizontalItemAdapter adapter = new HorizontalItemAdapter(dataItems);
                binding.rvHorizontalItem.setAdapter(adapter);

                getOnGoingAnime();
            }

            @Override
            public void onFailure(Call<JikanResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOnGoingAnime() {
        JikanApiService service = JikanApiClient.getRetrofitInstance().create(JikanApiService.class);
        Call<JikanResponseBody> call = service.getSeasonNow();
        call.enqueue(new Callback<JikanResponseBody>() {
            @Override
            public void onResponse(Call<JikanResponseBody> call, Response<JikanResponseBody> response) {
                progressDialog.dismiss();

                List<DataItem> dataItems = response.body().getData();
                VerticalItemAdapter adapter = new VerticalItemAdapter(dataItems);
                binding.rvVerticalItem.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JikanResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}