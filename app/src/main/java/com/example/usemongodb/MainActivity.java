package com.example.usemongodb;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.usemongodb.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initializing Retrofit and ApiService
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-skr0.onrender.com") // Replace with your backend URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);


        setSupportActionBar(binding.toolBar);

        fetchCurrentUserDetails();
    }

    private void fetchCurrentUserDetails() {
        Call<C_User> call = apiService.getCurrentUser();
        call.enqueue(new Callback<C_User>() {
            @Override
            public void onResponse(Call<C_User> call, Response<C_User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User details fetched successfully", Toast.LENGTH_SHORT).show();
                    //user name and email show in toolbar
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch user details: "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<C_User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.fetch) {
            // Handle settings action
            return true;
        } else if (id == R.id.logOut) {
            // Handle about action
//            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}