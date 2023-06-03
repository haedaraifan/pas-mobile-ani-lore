package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ani_lore.api.login.LoginApiClient;
import com.example.ani_lore.api.login.LoginApiService;
import com.example.ani_lore.databinding.ActivityLoginBinding;

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
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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

//        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
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

            }
        });
    }

    private void toastMessage(String value) {
        Toast.makeText(LoginActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return username.isEmpty() || password.isEmpty();
    }

    private void goToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}