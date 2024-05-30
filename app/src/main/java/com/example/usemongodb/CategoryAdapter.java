package com.example.usemongodb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Category_Model> categoryModels;
    private final OnCategoryClickListener listener;
    private int selectedPosition = 0;

    public interface OnCategoryClickListener {
        void onCategoryClick(String categoryName);
    }

    public CategoryAdapter(Context context, ArrayList<Category_Model> categoryModels, OnCategoryClickListener listener) {
        this.context = context;
        this.categoryModels = categoryModels;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryName.setText(categoryModels.get(position).getCategory_name());
        holder.categoryImage.setImageResource(categoryModels.get(position).getCategory_image());

        // Update background based on selection
        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.select_cat_item_bg); // Change to your selected background
        } else {
            holder.itemView.setBackgroundResource(R.drawable.cat_item_bg); // Default background
        }

        holder.itemView.setOnClickListener(v -> {
            // Update the selected position
            int previousPosition = selectedPosition;
            selectedPosition = position;
            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);

            // Notify listener
            listener.onCategoryClick(categoryModels.get(position).getCategory_name());
        });
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.cat_name);
            categoryImage = itemView.findViewById(R.id.cat_icon);
        }
    }
}
