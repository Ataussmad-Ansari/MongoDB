package com.example.usemongodb;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usemongodb.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    String name, email, pass;
    ApiService apiService;
    ActivityLoginBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        if (sharedPreferences.getString("logged", "false").equals("true")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        // Use HTTP for local testing. Ensure the backend server is accessible from the Android device.
        Retrofit retrofit = RetrofitClient.getClient("https://backend-skr0.onrender.com");
        apiService = retrofit.create(ApiService.class);

        binding.signup.setOnClickListener(v -> {
            name = binding.signupName.getText().toString();
            email = binding.signupEmail.getText().toString();
            pass = binding.signupPassword.getText().toString();

            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(name, email, pass);
            registerUser(newUser);
        });

        binding.login.setOnClickListener(v -> {
            email = binding.loginEmail.getText().toString();
            pass = binding.loginPassword.getText().toString();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            LoginUser loginUser = new LoginUser(email, pass);
            loginUser(loginUser);
        });

        binding.navLogin.setOnClickListener(v -> {

            binding.signupForm.setVisibility(View.GONE);
            binding.loginForm.setVisibility(View.VISIBLE);
            binding.navLogin.setBackgroundResource(R.drawable.nav_bg);
            binding.navSignup.setBackground(null);

        });

        binding.navSignup.setOnClickListener(v -> {

            binding.signupForm.setVisibility(View.VISIBLE);
            binding.loginForm.setVisibility(View.GONE);
            binding.navSignup.setBackgroundResource(R.drawable.nav_bg);
            binding.navLogin.setBackground(null);

        });
    }

    /*private void loginUser(LoginUser loginUser) {
        Call<Void> call = apiService.loginUser(loginUser);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Void b = response.body();

                    Toast.makeText(LoginActivity.this, "User logged in successfully "+b, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("logged", "true");
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();

                    binding.loginEmail.setText("");
                    binding.loginPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("LoginError", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                binding.loginEmail.setText("");
                binding.loginPassword.setText("");
            }
        });
    }*/

    private void loginUser(LoginUser loginUser) {
        Call<User> call = apiService.loginUser(loginUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("logged", "true");
                    editor.putString("name", user.getName());
                    editor.putString("email", user.getEmail());
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name", user.getName());
                    intent.putExtra("email", user.getEmail());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    binding.loginEmail.setText("");
                    binding.loginPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("LoginError", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loginEmail.setText("");
                binding.loginPassword.setText("");
            }
        });
    }


    private void registerUser(User user) {
        Call<Void> call = apiService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                    binding.signupName.setText("");
                    binding.signupEmail.setText("");
                    binding.signupPassword.setText("");

                    binding.signupForm.setVisibility(View.GONE);
                    binding.loginForm.setVisibility(View.VISIBLE);
                    binding.navLogin.setBackgroundResource(R.drawable.nav_bg);
                    binding.navSignup.setBackground(null);
                } else {
                    Toast.makeText(LoginActivity.this, "Registration failed: " + response.message(), Toast.LENGTH_SHORT).show();

                    binding.signupName.setText("");
                    binding.signupEmail.setText("");
                    binding.signupPassword.setText("");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("RegisterError", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                binding.signupName.setText("");
                binding.signupEmail.setText("");
                binding.signupPassword.setText("");
            }
        });
    }



    private void flipCard(View front, View back) {
        @SuppressLint("ResourceType") AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.out);
        @SuppressLint("ResourceType") AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.anim.in);

        setOut.setTarget(front);
        setIn.setTarget(back);
        setOut.start();
        setIn.start();

        front.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
    }
}