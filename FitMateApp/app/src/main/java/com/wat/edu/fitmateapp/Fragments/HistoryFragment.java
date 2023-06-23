package com.wat.edu.fitmateapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wat.edu.fitmateapp.Adapters.ActivityRecordAdapter;
import com.wat.edu.fitmateapp.Adapters.MealRecordAdapter;
import com.wat.edu.fitmateapp.Model.ActivityRecord;
import com.wat.edu.fitmateapp.Model.MealRecord;
import com.wat.edu.fitmateapp.R;
import com.wat.edu.fitmateapp.Viewmodel.MealActivityViewModel;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerViewMealRecords;
    private RecyclerView recyclerViewActivityRecords;
    private TextView textViewNoMealRecords;
    private TextView textViewNoActivityRecords;
    private MealRecordAdapter mealRecordAdapter;
    private ActivityRecordAdapter activityRecordAdapter;
    private final List<MealRecord> mealRecords;
    private final List<ActivityRecord> activityRecords;

    public HistoryFragment() {
        mealRecords = new ArrayList<>();
        activityRecords = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerViewMealRecords = view.findViewById(R.id.recyclerViewMealRecords);
        recyclerViewActivityRecords = view.findViewById(R.id.recyclerViewActivityRecords);
        textViewNoMealRecords = view.findViewById(R.id.textViewNoMealRecords);
        textViewNoActivityRecords = view.findViewById(R.id.textViewNoActivityRecords);

        mealRecordAdapter = new MealRecordAdapter(requireActivity(), mealRecords);
        activityRecordAdapter = new ActivityRecordAdapter(requireActivity(), activityRecords);

        recyclerViewMealRecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMealRecords.setAdapter(mealRecordAdapter);
        recyclerViewActivityRecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewActivityRecords.setAdapter(activityRecordAdapter);

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MealActivityViewModel mealActivityViewModel = new ViewModelProvider(this).get(MealActivityViewModel.class);
        mealActivityViewModel.getAllMealRecords().observe(getViewLifecycleOwner(), records -> {
            mealRecords.clear();
            mealRecords.addAll(records);
            mealRecordAdapter.notifyDataSetChanged();

            if (mealRecords.isEmpty()) {
                textViewNoMealRecords.setVisibility(View.VISIBLE);
                recyclerViewMealRecords.setVisibility(View.GONE);
            } else {
                textViewNoMealRecords.setVisibility(View.GONE);
                recyclerViewMealRecords.setVisibility(View.VISIBLE);
            }
        });
        mealActivityViewModel.getAllActivityRecords().observe(getViewLifecycleOwner(), records -> {
            activityRecords.clear();
            activityRecords.addAll(records);
            activityRecordAdapter.notifyDataSetChanged();

            if (activityRecords.isEmpty()) {
                textViewNoActivityRecords.setVisibility(View.VISIBLE);
                recyclerViewActivityRecords.setVisibility(View.GONE);
            } else {
                textViewNoActivityRecords.setVisibility(View.GONE);
                recyclerViewActivityRecords.setVisibility(View.VISIBLE);
            }
        });
    }
}