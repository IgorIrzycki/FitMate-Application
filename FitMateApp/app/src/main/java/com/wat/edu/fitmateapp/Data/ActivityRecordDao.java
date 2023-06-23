package com.wat.edu.fitmateapp.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.wat.edu.fitmateapp.Model.ActivityRecord;


import java.util.List;

@Dao
public interface ActivityRecordDao {
    @Insert
    void insertActivityRecord(ActivityRecord activityRecord);

    @Delete
    void deleteActivityRecord(ActivityRecord activityRecord);

    @Query("SELECT * FROM activity_records")
    LiveData<List<ActivityRecord>> getAllActivityRecords();

}
