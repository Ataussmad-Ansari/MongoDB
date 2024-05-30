package com.example.usemongodb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;

    public FoodAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food foodItem = foodList.get(position);
        holder.foodName.setText(foodItem.getName());
        holder.foodCalories.setText(foodItem.getCalories());
        holder.foodPrice.setText(foodItem.getPrice());
        holder.foodImage.setImageResource(foodItem.getImageResId());
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void updateFoodItem(List<Food> foods) {
        this.foodList = foods;
        notifyDataSetChanged();
    }


    public class FoodViewHolder extends RecyclerView.ViewHolder {
        ImageView foodImage;
        TextView foodName, foodCalories, foodPrice;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodName);
            foodCalories = itemView.findViewById(R.id.foodCalories);
            foodPrice = itemView.findViewById(R.id.foodPrice);
        }
    }
}

