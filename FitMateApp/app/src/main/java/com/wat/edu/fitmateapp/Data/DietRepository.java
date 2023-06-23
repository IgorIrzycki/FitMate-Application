package com.wat.edu.fitmateapp.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.wat.edu.fitmateapp.Model.ActivityRecord;
import com.wat.edu.fitmateapp.Model.MealRecord;

import java.util.List;

public class DietRepository {
    private final MealRecordDao mealRecordDao;
    private final ActivityRecordDao activityRecordDao;

    private final LiveData<List<MealRecord>> mAllMealRecords;
    private final LiveData<List<ActivityRecord>> mAllActivityRecords;

    public DietRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mealRecordDao = db.mealRecordDao();
        activityRecordDao = db.activityRecordDao();
        mAllMealRecords = mealRecordDao.getAllMealRecords();
        mAllActivityRecords = activityRecordDao.getAllActivityRecords();
    }

    public LiveData<List<MealRecord>> getAllMealRecords() {
        return mAllMealRecords;
    }

    public void insertMealRecord(MealRecord mealRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> mealRecordDao.insertMealRecord(mealRecord));
    }

    public void deleteMealRecord(MealRecord mealRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> mealRecordDao.deleteMealRecord(mealRecord));
    }

    public LiveData<List<ActivityRecord>> getAllActivityRecords() {
        return mAllActivityRecords;
    }

    public void insertActivityRecord(ActivityRecord activityRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> activityRecordDao.insertActivityRecord(activityRecord));
    }

    public void deleteActivityRecord(ActivityRecord activityRecord) {
        AppDatabase.databaseWriteExecutor.execute(() -> activityRecordDao.deleteActivityRecord(activityRecord));
    }
}
