package com.example.usemongodb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.usemongodb.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private ApiService apiService;
    private String name, email;

    CategoryAdapter adapter;
    ArrayList<Category_Model> categoryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://backend-skr0.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        // Get user details from Intent or SharedPreferences
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");

        if (name == null || email == null) {
            name = sharedPreferences.getString("name", "Guest");
            email = sharedPreferences.getString("email", "No Email");
        }
        binding.profile.setOnClickListener(v -> {
            userData();
        });

        binding.menu.setOnClickListener(v -> {
            binding.slider.setVisibility(View.VISIBLE);
        });
        binding.closeSlider.setOnClickListener(v -> {
            binding.slider.setVisibility(View.GONE);
        });
        binding.logout.setOnClickListener(v -> {
            logout();
        });
        binding.userData.setOnClickListener(v -> {
            userData();
        });


        getCategories();

        select_nav_item();

    }

    private void getCategories() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        binding.catRV.setLayoutManager(staggeredGridLayoutManager);
        categoryModels.add(new Category_Model("Fast food", R.drawable.burger));
        categoryModels.add(new Category_Model("Fruit item", R.drawable.banana));
        categoryModels.add(new Category_Model("Vegetable", R.drawable.burger));
        categoryModels.add(new Category_Model("Drink", R.drawable.drink));
        categoryModels.add(new Category_Model("Meat", R.drawable.meat));
        adapter = new CategoryAdapter(this, categoryModels);
        binding.catRV.setAdapter(adapter);
    }

    private void select_nav_item() {
        binding.home.setOnClickListener(v -> {
            binding.menus.setBackground(null);
            binding.cart.setBackground(null);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.home.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.menus.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackground(null);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.menus.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.cart.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.menus.setBackground(null);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.bookmark.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackground(null);
            binding.menus.setBackground(null);
            binding.bell.setBackground(null);
            binding.bookmark.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.bell.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackground(null);
            binding.bookmark.setBackground(null);
            binding.menus.setBackground(null);
            binding.bell.setBackgroundResource(R.drawable.nav_sel_bg);
        });
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void userData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("User Details");
        builder.setMessage("Name: " + name + "\nEmail: " + email);
        builder.setPositiveButton("OK", null);
        builder.show();
    }


}
