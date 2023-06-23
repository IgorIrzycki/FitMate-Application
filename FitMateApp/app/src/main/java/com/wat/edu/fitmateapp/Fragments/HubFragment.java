package com.wat.edu.fitmateapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.wat.edu.fitmateapp.R;

public class HubFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hub, container, false);

        Button mealsButton = view.findViewById(R.id.buttonMeals);
        Button historyButton = view.findViewById(R.id.buttonHistory);
        Button activityButton = view.findViewById(R.id.buttonActivity);

        mealsButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_hubFragment_to_mealFragment));
        activityButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_hubFragment_to_activityFragment));
        historyButton.setOnClickListener(v -> Navigation.findNavController(requireView()).navigate(R.id.action_hubFragment_to_historyFragment));

        return view;
    }

}
