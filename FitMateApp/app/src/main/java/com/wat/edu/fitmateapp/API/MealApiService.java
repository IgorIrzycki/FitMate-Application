package com.wat.edu.fitmateapp.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("foods/search")
    Call<MealApiResponse> searchMealByName(
            @Query("query") String query,
            @Query("api_key") String apiKey,
            @Query("dataType") String dataType,
            @Query("sortBy") String sortBy,
            @Query("sortOrder") String sortOrder,
            @Query("pageSize") int pageSize
    );

}
