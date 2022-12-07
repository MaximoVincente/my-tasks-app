package com.maximo.mytaskmanager.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.database.MyTaskManagerDatabase;
import com.maximo.mytaskmanager.models.Task;

import java.util.Date;

public class AddTask extends AppCompatActivity {

    MyTaskManagerDatabase myTaskManagerDatabase;
    Spinner taskStateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskStateSpinner = findViewById(R.id.AddTaskActivitySpinnerState);

        myTaskManagerDatabase = Room.databaseBuilder(
            getApplicationContext(),
            MyTaskManagerDatabase.class,
            MainActivity.DATABASE_NAME)
            .fallbackToDestructiveMigration() // If Room gets confused, it tosses your database; don't use this in production!
            .allowMainThreadQueries()
            .build();
            setupTypeSpinner();
            setupAddTaskButton();

    }

    public void setupTypeSpinner(){
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                taskStateSpinner.getContext(),
                android.R.layout.simple_spinner_item,
                Task.TaskStateEnum.values()
        ));
    }

    public void setupAddTaskButton(){
        Button addTaskButton = findViewById(R.id.AddTaskActivitySubmitTaskButton);
        addTaskButton.setOnClickListener(view -> {
            Task newTask = new Task(
                ((EditText)findViewById(R.id.AddTaskActivityEditTextTaskTitle)).getText().toString(),
                ((EditText)findViewById(R.id.AddTaskActivityEditTextTaskDescription)).getText().toString(),
                Task.TaskStateEnum.fromString(taskStateSpinner.getSelectedItem().toString())
            );
            myTaskManagerDatabase.taskDao().insertATask(newTask);
            Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}


