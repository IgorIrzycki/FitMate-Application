package com.wat.edu.fitmateapp.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.wat.edu.fitmateapp.Model.MealRecord;
import com.wat.edu.fitmateapp.R;
import com.wat.edu.fitmateapp.Viewmodel.MealActivityViewModel;

import java.util.List;

public class MealRecordAdapter extends RecyclerView.Adapter<MealRecordAdapter.MealViewHolder> {

    private final List<MealRecord> meals;
    private final FragmentActivity activity;
    private MealActivityViewModel mealActivityViewModel;

    public MealRecordAdapter(FragmentActivity activity, List<MealRecord> meals) {
        this.activity = activity;
        this.meals = meals;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_records, parent, false);
        return new MealViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        MealRecord meal = meals.get(position);
        holder.textViewMeal.setText("Meal");
        holder.textViewDate.setText("Date: " + meal.getDate());
        holder.textViewTotalCalories.setText("Total Calories: " + meal.getTotalCalories());

        holder.buttonRemove.setOnClickListener(view -> {
            mealActivityViewModel = new ViewModelProvider(activity).get(MealActivityViewModel.class);
            mealActivityViewModel.deleteMealRecord(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMeal;
        TextView textViewDate;
        TextView textViewTotalCalories;
        ImageButton buttonRemove;

        MealViewHolder(View itemView) {
            super(itemView);
            textViewMeal = itemView.findViewById(R.id.textViewMeal);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTotalCalories = itemView.findViewById(R.id.textViewTotalCalories);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }

}
