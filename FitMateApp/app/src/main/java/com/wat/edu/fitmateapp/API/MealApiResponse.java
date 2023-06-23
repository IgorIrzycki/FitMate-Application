package com.wat.edu.fitmateapp.API;

import com.google.gson.annotations.SerializedName;
import com.wat.edu.fitmateapp.Model.Meal;

import java.util.List;

public class MealApiResponse {
    @SerializedName("foods")
    private List<Meal> meals;

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
