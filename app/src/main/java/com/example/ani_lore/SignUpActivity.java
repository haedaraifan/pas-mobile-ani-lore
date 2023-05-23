package com.example.ani_lore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.ani_lore.api.login.LoginApiClient;
import com.example.ani_lore.api.login.LoginApiService;
import com.example.ani_lore.databinding.ActivitySignUpBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private ProgressDialog progressDialog;
    private String fullName, username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = binding.edFullName.getText().toString();
                username = binding.edUsername.getText().toString();
                email = binding.edEmail.getText().toString();
                password = binding.edPassword.getText().toString();
                confirmPassword = binding.edConfirmPassword.getText().toString();

                if(formIsEmpty()) {
                    toastMessage("All fields is required and cannot be empty");
                }
                else if(!isUsernameValid(username)) {
                    toastMessage("Please enter a valid username");
                }
                else if(!isEmailValid(email)) {
                    toastMessage("Please enter a valid email address");
                }
                else if(!isMinimalPasswordValid(password) || !isMinimalPasswordValid(confirmPassword)) {
                    toastMessage("Password must be at least 8 characters");
                }
                else if(!isPasswordMatch()) {
                    toastMessage("Password and confirmation password do not match");
                }
                else {
                    signUpProcess();
                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
    }

    private void signUpProcess() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();

        LoginApiService service = LoginApiClient.getRetrofitInstance().create(LoginApiService.class);
        Call<ResponseBody> call = service.registerUser(username, fullName, email, password);
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
                        String message = jsonResponse.getString("status");

                        toastMessage(message);

                        if(status) {
                            goToLoginActivity();
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
        Toast.makeText(SignUpActivity.this, value, Toast.LENGTH_SHORT).show();
    }

    private boolean formIsEmpty() {
        return fullName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
    }

    private boolean isUsernameValid(String value) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_]+");
        return pattern.matcher(value)
                .matches();
    }

    private boolean isEmailValid(String value) {
        return Patterns.EMAIL_ADDRESS.matcher(value)
                .matches();
    }

    private boolean isMinimalPasswordValid(String value) {
        return value.length() >= 8;
    }

    private boolean isPasswordMatch() {
        return password.equals(confirmPassword);
    }

    private void goToLoginActivity() {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
    }
}