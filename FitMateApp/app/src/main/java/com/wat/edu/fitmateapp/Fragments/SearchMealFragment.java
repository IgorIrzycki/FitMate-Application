package com.wat.edu.fitmateapp.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wat.edu.fitmateapp.API.MealApiClient;
import com.wat.edu.fitmateapp.API.MealApiResponse;
import com.wat.edu.fitmateapp.Adapters.MealAdapter;
import com.wat.edu.fitmateapp.Model.Meal;
import com.wat.edu.fitmateapp.Model.MealRecord;
import com.wat.edu.fitmateapp.R;
import com.wat.edu.fitmateapp.Viewmodel.MealActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMealFragment extends Fragment implements SearchView.OnQueryTextListener, MealAdapter.MealClickListener {

    private final List<Meal> filteredMeals;
    private MealAdapter mealAdapter;
    private MealActivityViewModel mealActivityViewModel;
    private int totalCalories = 0;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewMeals;

    public SearchMealFragment() {
        filteredMeals = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_meal, container, false);

        SearchView searchViewMeals = view.findViewById(R.id.searchViewMeals);
        TextView textViewChosenDate = view.findViewById(R.id.textViewChosenDate);
        recyclerViewMeals = view.findViewById(R.id.recyclerViewMeals);
        Button buttonSaveMeal = view.findViewById(R.id.buttonSaveMeal);
        progressBar = view.findViewById(R.id.progressBar);

        if (getArguments() != null) {
            long selectedDate = getArguments().getLong("selectedDate");
            String formattedDate = formatDate(selectedDate);
            textViewChosenDate.setText(String.format("Chosen date: %s", formattedDate));
            totalCalories = 0;
        }

        mealActivityViewModel = new ViewModelProvider(this).get(MealActivityViewModel.class);

        mealAdapter = new MealAdapter(filteredMeals, this);
        recyclerViewMeals.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMeals.setAdapter(mealAdapter);

        searchViewMeals.setOnQueryTextListener(this);

        buttonSaveMeal.setOnClickListener(v -> {
            if (totalCalories == 0) {
                Toast.makeText(getActivity(), "Cannot save the Meal Record, because you did not add any meals", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    long selectedDate = getArguments().getLong("selectedDate");
                    MealRecord mealRecord = new MealRecord();
                    mealRecord.setDate(formatDate(selectedDate));
                    mealRecord.setTotalCalories(totalCalories);
                    mealActivityViewModel.insertMealRecord(mealRecord);
                    Toast.makeText(getActivity(), "Meal Record saved, Total Calories: " + totalCalories, Toast.LENGTH_SHORT).show();
                    NavController navController = NavHostFragment.findNavController(SearchMealFragment.this);
                    navController.navigate(R.id.action_searchMealFragment_to_hubFragment);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error saving MealRecord", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onQueryTextSubmit(String query) {
        recyclerViewMeals.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        searchMeal(query);

        mealAdapter.notifyDataSetChanged();
        return true;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            filteredMeals.clear();
        }

        mealAdapter.notifyDataSetChanged();
        return true;
    }

    private String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date(date));
    }

    @Override
    public void onMealClick(int calories) {
        totalCalories += calories;
    }

    private void searchMeal(String query) {
        String apiKey = "d6ma6fY00FE5NQjKGReWhF3LDmC3DZJ3wIXWo7hn";
        String dataType = "Survey (FNDDS)";
        String sortBy = "lowercaseDescription.keyword";
        String sortOrder = "asc";
        int pageSize = 200;


        MealApiClient apiClient = new MealApiClient();
        Call<MealApiResponse> call = apiClient.searchMealByName(query, apiKey, dataType, sortBy, sortOrder, pageSize);


        call.enqueue(new Callback<MealApiResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MealApiResponse> call, @NonNull Response<MealApiResponse> response) {
                if (response.isSuccessful()) {
                    MealApiResponse foodResponse = response.body();
                    if (foodResponse != null) {
                        List<Meal> meals = foodResponse.getMeals();
                        filteredMeals.clear();
                        filteredMeals.addAll(meals);
                    }
                }

                mealAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerViewMeals.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<MealApiResponse> call, @NonNull Throwable t) {

            }
        });
    }

}

