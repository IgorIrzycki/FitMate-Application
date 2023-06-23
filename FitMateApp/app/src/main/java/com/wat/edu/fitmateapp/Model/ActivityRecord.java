package com.wat.edu.fitmateapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "activity_records")
public class ActivityRecord {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "record_id_activity")
    private int id;

    @ColumnInfo(name = "record_date_activity")
    private String date;

    @ColumnInfo(name = "record_distance")
    private String distance;

    @ColumnInfo(name = "record_calories_burned")
    private double caloriesBurned;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
