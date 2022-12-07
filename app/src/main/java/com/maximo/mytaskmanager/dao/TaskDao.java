package com.maximo.mytaskmanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.maximo.mytaskmanager.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    public void insertATask(Task task);

    @Query("SELECT * FROM Task")
    public List<Task> findAll();

    @Query("SELECT * FROM Task WHERE id = :id")
    public Task findById(long id);

    @Query("SELECT * FROM Task WHERE state = :state")
    public List<Task> findAllByType(Task.TaskStateEnum state);

    @Delete
    public void delete(Task task);

    @Update
    public void update(Task task);
}
