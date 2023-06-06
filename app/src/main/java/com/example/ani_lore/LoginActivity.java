package com.example.ani_lore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ani_lore.api.login.LoginApiClient;
import com.example.ani_lore.api.login.LoginApiService;
import com.example.ani_lore.databinding.ActivityLoginBinding;
import com.example.ani_lore.db.AppDatabase;
import com.example.ani_lore.db.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ProgressDialog progressDialog;
    private Preferences preferences;
    private AppDatabase db;
    private String username, password;
//    private GoogleSignInOptions gso;
//    private GoogleSignInClient gsc;
//    private static final int GOOGLE_SIGN_IN_RC = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        gsc = GoogleSignIn.getClient(this, gso);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-login").allowMainThreadQueries().build();

        preferences = new Preferences(this);
        if(preferences.getSessionLogin()) {
            goToMainActivity();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = binding.edUsername.getText().toString();
                password = binding.edPassword.getText().toString();

                if(username.equals("admin") && password.equals("admin")) {

                    insertUserDb(username);

                    preferences.setSessionLogin(true);
                    goToMainActivity();
                    return;
                }

                if(formIsEmpty()) {
                    toastMessage("All fields is required and cannot be empty");
                }
                else {
                    loginProcess();
                }
            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });

//        binding.googleRounded.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signInWithGoogle();
//            }
//        });
    }

    private void loginProcess() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        LoginApiService service = LoginApiClient.getRetrofitInstance().create(LoginApiService.class);
        Call<ResponseBody> call = service.login(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                String res = null;
                try {
                    res = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(res != null) {
                    try {
                        JSONObject jsonResponse = new JSONObject(res);

                        boolean status = jsonResponse.getBoolean("status");
                        String message = jsonResponse.getString("message");

                        toastMessage(message);

                        if(status) {

                            insertUserDb(username);

                            preferences.setSessionLogin(true);

                            goToMainActivity();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                toastMessage("Something went wrong...Please try later!");
            }
        });
    }

//    private void signInWithGoogle() {
//        Intent signInIntent = gsc.getSignInIntent();
//        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_RC);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == GOOGLE_SIGN_IN_RC) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//
//                insertUserDb(account.getDisplayName());
//                preferences.setSessionLogin(true);
//
//                goToMainActivity();
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void insertUserDb(String username) {
        User user = new User();
        user.username = username;
        user.profilePicUrl = null;
        db.userDao().insert(user);
    }

    private void toastMessage(String value) {
        Toast.makeText(LoginActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return username.isEmpty() || password.isEmpty();
    }

    private void goToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}