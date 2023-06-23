package com.wat.edu.fitmateapp.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wat.edu.fitmateapp.Model.Meal;
import com.wat.edu.fitmateapp.R;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private final List<Meal> meals;
    private final MealClickListener mealClickListener;

    public MealAdapter(List<Meal> meals, MealClickListener mealClickListener) {
        this.meals = meals;
        this.mealClickListener = mealClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.textViewMealName.setText(meal.getName());
        holder.textViewCalories.setText("Calories: " + meal.getCalories());

        holder.buttonAddMeal.setOnClickListener(view -> {
            int calories = meal.getCalories();
            mealClickListener.onMealClick(calories);
            Toast.makeText(view.getContext(), "Added " + calories + " calories to Meal Record", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMealName;
        TextView textViewCalories;
        ImageButton buttonAddMeal;

        MealViewHolder(View itemView) {
            super(itemView);
            textViewMealName = itemView.findViewById(R.id.textViewMealName);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            buttonAddMeal = itemView.findViewById(R.id.buttonAddMeal);

        }
    }

    public interface MealClickListener {
        void onMealClick(int calories);
    }
}


