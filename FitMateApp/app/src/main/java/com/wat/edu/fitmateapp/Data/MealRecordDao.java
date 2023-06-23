package com.wat.edu.fitmateapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wat.edu.fitmateapp.Model.MealRecord;

import java.util.List;

@Dao
public interface MealRecordDao {
    @Insert
    void insertMealRecord(MealRecord mealRecord);

    @Delete
    void deleteMealRecord(MealRecord mealRecord);

    @Query("SELECT * FROM meals")
    LiveData<List<MealRecord>> getAllMealRecords();
}
