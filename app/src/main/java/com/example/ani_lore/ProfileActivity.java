package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ani_lore.databinding.ActivityProfileBinding;
import com.example.ani_lore.databinding.ActivitySearchBinding;
import com.example.ani_lore.db.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private Preferences preferences;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-login").allowMainThreadQueries().build();

        preferences = new Preferences(this);
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("LOGOUT", "logout");

                db.userDao().deleteAll();

                Log.d("DELETE DB", "deleted");

                preferences.setSessionLogin(false);
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish();
                    return true;
                case R.id.search:
                    startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
                    finish();
                    return true;
                case R.id.profile:
                    return true;
            }
            return false;
        });
    }


}