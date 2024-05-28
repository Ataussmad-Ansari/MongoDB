package com.example.usemongodb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.usemongodb.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements CategoryAdapter.OnCategoryClickListener {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private String name, email;
    private CategoryAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private final ArrayList<Category_Model> categoryModels = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private final List<Food> allFoods = new ArrayList<>();

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        initSharedPreferences();
        setupProfileClickListener();
        setupMenuClickListeners();
        initializeCategories();
        initializeFoods();
        selectNavItem();
        updateFoodsByCategory("Fast food");
    }


    private void initSharedPreferences() {
        sharedPreferences = getSharedPreferences("MyAppName", MODE_PRIVATE);
    }

    private void setupProfileClickListener() {
        binding.profile.setOnClickListener(v -> showUserDataDialog());
    }

    private void setupMenuClickListeners() {
        binding.menu.setOnClickListener(v -> binding.slider.setVisibility(View.VISIBLE));
        binding.closeSlider.setOnClickListener(v -> binding.slider.setVisibility(View.GONE));
        binding.logout.setOnClickListener(v -> logout());
        binding.userData.setOnClickListener(v -> showUserDataDialog());
    }

    private void initializeCategories() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        binding.catRV.setLayoutManager(layoutManager);

        categoryModels.add(new Category_Model("Fast food", R.drawable.burger));
        categoryModels.add(new Category_Model("Fruit item", R.drawable.banana));
        categoryModels.add(new Category_Model("Vegetable", R.drawable.burger));
        categoryModels.add(new Category_Model("Drink", R.drawable.drink));
        categoryModels.add(new Category_Model("Meat", R.drawable.meat));

        categoryAdapter = new CategoryAdapter(this, categoryModels, this);
        binding.catRV.setAdapter(categoryAdapter);

        // Scroll to the default selected position
        binding.catRV.scrollToPosition(0);
    }

    private void initializeFoods() {
        allFoods.clear();

        CenterZoomLayoutManager layoutManager = new CenterZoomLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.foodRV.setLayoutManager(layoutManager);

        allFoods.add(new Food("Cheeseburger", "78 Calories", "$ 9.50", "Fast food", R.drawable.bell));
        allFoods.add(new Food("Banana", "52 Calories", "$ 1.50", "Fruit item", R.drawable.bell));
        allFoods.add(new Food("Broccoli", "25 Calories", "$ 1.20", "Vegetable", R.drawable.bell));
        allFoods.add(new Food("Coca-Cola", "140 Calories", "$ 1.00", "Drink", R.drawable.bell));
        allFoods.add(new Food("Turkey Breast", "600 Calories", "$ 15.00", "Meat", R.drawable.bell));
        allFoods.add(new Food("Pepperoni Pizza", "78 Calories", "$ 9.50", "Fast food", R.drawable.bell));
        allFoods.add(new Food("Lemonade", "140 Calories", "$ 1.00", "Drink", R.drawable.bell));
        allFoods.add(new Food("Apple", "52 Calories", "$ 1.50", "Fruit item", R.drawable.bell));
        allFoods.add(new Food("Spinach", "25 Calories", "$ 1.20", "Vegetable", R.drawable.bell));
        allFoods.add(new Food("Pork Ribs", "600 Calories", "$ 15.00", "Meat", R.drawable.bell));
        allFoods.add(new Food("Chicken Nuggets", "78 Calories", "$ 9.50", "Fast food", R.drawable.bell));
        allFoods.add(new Food("Mango", "52 Calories", "$ 1.50", "Fruit item", R.drawable.bell));
        allFoods.add(new Food("Carrot", "25 Calories", "$ 1.20", "Vegetable", R.drawable.bell));
        allFoods.add(new Food("Coke", "140 Calories", "$ 1.00", "Drink", R.drawable.bell));
        allFoods.add(new Food("Steak", "600 Calories", "$ 15.00", "Meat", R.drawable.bell));

        foods.addAll(allFoods);  // Initially show all foods

        foodAdapter = new FoodAdapter(foods);
        binding.foodRV.setAdapter(foodAdapter);

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(binding.foodRV);
    }


    private void updateFoodsByCategory(String category) {
        foods.clear();
        if (category.equals("All")) {
            foods.addAll(allFoods);
        } else {
            for (Food food : allFoods) {
                if (food.getCategory_name().equals(category)) {
                    foods.add(food);
                }
            }
        }
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(String categoryName) {
        updateFoodsByCategory(categoryName);
    }

    private void selectNavItem() {
        binding.home.setOnClickListener(v -> {
            binding.menus.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.center_nav_bg);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.home.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.menus.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.center_nav_bg);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.menus.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.cart.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.menus.setBackground(null);
            binding.bookmark.setBackground(null);
            binding.bell.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.sel_cen_nav_bg);
        });
        binding.bookmark.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.center_nav_bg);
            binding.menus.setBackground(null);
            binding.bell.setBackground(null);
            binding.bookmark.setBackgroundResource(R.drawable.nav_sel_bg);
        });
        binding.bell.setOnClickListener(v -> {
            binding.home.setBackground(null);
            binding.cart.setBackgroundResource(R.drawable.center_nav_bg);
            binding.bookmark.setBackground(null);
            binding.menus.setBackground(null);
            binding.bell.setBackgroundResource(R.drawable.nav_sel_bg);
        });
    }

    private void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("name");
        editor.remove("email");
        editor.apply();
        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showUserDataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("User Details");
        builder.setMessage("Name: " + name + "\nEmail: " + email);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
