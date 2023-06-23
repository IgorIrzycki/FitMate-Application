package com.wat.edu.fitmateapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.wat.edu.fitmateapp.R;

import java.util.Calendar;

public class MealFragment extends Fragment {

    private long selectedDate;

    public MealFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);
        Button buttonDone = view.findViewById(R.id.buttonDone);

        calendarView.setDate(System.currentTimeMillis());
        selectedDate = calendarView.getDate();

        buttonDone.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(MealFragment.this);
            Bundle args = new Bundle();
            args.putLong("selectedDate", selectedDate);
            navController.navigate(R.id.action_mealFragment_to_searchMealFragment, args);
        });

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            selectedDate = calendar.getTimeInMillis();
        });

        return view;
    }
}
