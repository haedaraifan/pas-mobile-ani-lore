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
import com.example.ani_lore.db.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private ActivityProfileBinding binding;
    private Preferences preferences;
    private AppDatabase db;
//    private GoogleSignInOptions gso;
//    private GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        gsc = GoogleSignIn.getClient(this, gso);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-login").allowMainThreadQueries().build();

        List<User> users = db.userDao().getAll();
        if(!users.isEmpty()) {
            binding.nameProfile.setText(users.get(0).username);
        }

        preferences = new Preferences(this);
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                gsc.signOut();
                db.userDao().deleteAll();
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