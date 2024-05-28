package com.example.usemongodb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    Context context;
    ArrayList<Category_Model> categoryModels;

    public CategoryAdapter(Context context, ArrayList<Category_Model> categoryModels) {
        this.context = context;
        this.categoryModels = categoryModels;
    }

    @NonNull
    @Override
    public CategoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewHolder holder, int position) {
        holder.categoryName.setText(categoryModels.get(position).getCategory_name());
//        Glide.with(context).load(categoryModels.get(position).getCategory_image()).into(holder.categoryImage);
        holder.categoryImage.setImageResource(categoryModels.get(position).getCategory_image());
    }

    @Override
    public int getItemCount() {
        return categoryModels.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryImage;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.cat_name);
            categoryImage = itemView.findViewById(R.id.cat_icon);
        }
    }
}
