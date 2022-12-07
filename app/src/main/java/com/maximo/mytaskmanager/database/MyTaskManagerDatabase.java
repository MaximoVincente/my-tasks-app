package com.maximo.mytaskmanager.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.maximo.mytaskmanager.dao.TaskDao;
import com.maximo.mytaskmanager.models.Task;

@TypeConverters({MyTaskManagerDatabaseConverters.class})

@Database(entities = {Task.class}, version = 1)
public abstract class MyTaskManagerDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}
