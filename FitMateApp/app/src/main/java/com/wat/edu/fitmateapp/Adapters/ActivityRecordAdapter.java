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

import com.wat.edu.fitmateapp.Model.ActivityRecord;
import com.wat.edu.fitmateapp.R;
import com.wat.edu.fitmateapp.Viewmodel.MealActivityViewModel;

import java.util.List;

public class ActivityRecordAdapter extends RecyclerView.Adapter<ActivityRecordAdapter.MealViewHolder> {

    private final List<ActivityRecord> activities;
    private final FragmentActivity activity;
    private MealActivityViewModel mealActivityViewModel;

    public ActivityRecordAdapter(FragmentActivity activity, List<ActivityRecord> activities) {
        this.activity = activity;
        this.activities = activities;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_records, parent, false);
        return new MealViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        ActivityRecord activityRecord = activities.get(position);
        holder.textViewDateActivity.setText("Date: " + activityRecord.getDate());
        holder.textViewActivity.setText("Activity");
        holder.textViewDistanceRecord.setText("Distance: " + activityRecord.getDistance());
        holder.textViewCaloriesBurned.setText("Calories burned: " + activityRecord.getCaloriesBurned());

        holder.buttonRemove.setOnClickListener(view -> {
            mealActivityViewModel = new ViewModelProvider(activity).get(MealActivityViewModel.class);
            mealActivityViewModel.deleteActivityRecord(activityRecord);
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView textViewActivity;
        TextView textViewDateActivity;
        TextView textViewCaloriesBurned;
        TextView textViewDistanceRecord;
        ImageButton buttonRemove;

        MealViewHolder(View itemView) {
            super(itemView);
            textViewActivity = itemView.findViewById(R.id.textViewActivity);
            textViewDateActivity = itemView.findViewById(R.id.textViewDateActivity);
            textViewCaloriesBurned = itemView.findViewById(R.id.textViewCaloriesBurned);
            textViewDistanceRecord = itemView.findViewById(R.id.textViewDistanceRecord);
            buttonRemove = itemView.findViewById(R.id.buttonRemove);
        }
    }

}
