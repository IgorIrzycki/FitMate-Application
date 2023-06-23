package com.wat.edu.fitmateapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meal {
    @SerializedName("description")
    private String name;

    public Meal(List<FoodNutrient> foodNutrients) {
        this.foodNutrients = foodNutrients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("foodNutrients")
    private final List<FoodNutrient> foodNutrients;


    public int getCalories() {
        if (foodNutrients != null && !foodNutrients.isEmpty()) {
            for (FoodNutrient nutrient : foodNutrients) {
                if (nutrient.getName().equals("Energy")) {
                    return (int) nutrient.getAmount();
                }
            }
        }
        return 0;
    }


    private static class FoodNutrient {
        @SerializedName("nutrientName")
        private final String name;
        @SerializedName("value")
        private final double amount;

        private FoodNutrient(String name, double amount) {
            this.name = name;
            this.amount = amount;
        }

        public String getName() {
            return name;
        }

        public double getAmount() {
            return amount;
        }
    }
}
