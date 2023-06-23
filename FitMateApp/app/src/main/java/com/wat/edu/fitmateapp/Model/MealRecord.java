package com.wat.edu.fitmateapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meals")
public class MealRecord {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id")
    private int id;
    @ColumnInfo(name = "record_date")
    private String date;
    @ColumnInfo(name = "record_total_calories")
    private int totalCalories;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

}
