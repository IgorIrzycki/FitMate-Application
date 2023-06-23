package com.wat.edu.fitmateapp.API;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealApiClient {
    private static final String BASE_URL = "https://api.nal.usda.gov/fdc/v1/";

    private final MealApiService apiService;

    public MealApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(MealApiService.class);
    }

    public Call<MealApiResponse> searchMealByName(String query, String apiKey, String dataType, String sortBy, String sortOrder, int pageSize) {
        return apiService.searchMealByName(query, apiKey, dataType, sortBy, sortOrder, pageSize);
    }
}

