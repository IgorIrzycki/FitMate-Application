package com.wat.edu.fitmateapp.Viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.wat.edu.fitmateapp.Model.ActivityRecord;
import com.wat.edu.fitmateapp.Model.MealRecord;
import com.wat.edu.fitmateapp.Data.DietRepository;

import java.util.List;

public class MealActivityViewModel extends AndroidViewModel {
    private final DietRepository mRepository;

    private final LiveData<List<MealRecord>> mAllMealRecords;
    private final LiveData<List<ActivityRecord>> mAllActivityRecords;

    public MealActivityViewModel(Application application) {
        super(application);
        mRepository = new DietRepository(application);
        mAllMealRecords = mRepository.getAllMealRecords();
        mAllActivityRecords = mRepository.getAllActivityRecords();
    }

    public LiveData<List<MealRecord>> getAllMealRecords() {
        return mAllMealRecords;
    }

    public void insertMealRecord(MealRecord mealRecord) {
        mRepository.insertMealRecord(mealRecord);
    }

    public void deleteMealRecord(MealRecord mealRecord) {
        mRepository.deleteMealRecord(mealRecord);
    }

    public LiveData<List<ActivityRecord>> getAllActivityRecords() {
        return mAllActivityRecords;
    }

    public void insertActivityRecord(ActivityRecord activityRecord) {
        mRepository.insertActivityRecord(activityRecord);
    }

    public void deleteActivityRecord(ActivityRecord activityRecord) {
        mRepository.deleteActivityRecord(activityRecord);
    }
}
