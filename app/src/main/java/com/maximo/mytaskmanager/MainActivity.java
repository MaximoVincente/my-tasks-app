package com.maximo.mytaskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String MY_TASK_EXTRA_TAG = "myTask";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAddTaskButton();
        setupAllTasksButton();
        setupSettingsPageImageButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUsernameDisplay();
        setupTaskTextViewLinks();
    }


//Button to go to to the Add Task activity

    public void setupAddTaskButton(){
        Button addToTaskButton = MainActivity.this.findViewById(R.id.MainActivityAddTaskButton);
        addToTaskButton.setOnClickListener(view -> {
            Intent goToAddTaskActivity = new Intent(this, AddTask.class);
            startActivity(goToAddTaskActivity);
        });
    }

    public void setupAllTasksButton(){
        Button allTasksButton = MainActivity.this.findViewById(R.id.MainActivityAllTasksButton);
        allTasksButton.setOnClickListener(view -> {
            Intent goToAddTaskActivity = new Intent(this, AllTasks.class);
            startActivity(goToAddTaskActivity);
        });
    }

    public void setupSettingsPageImageButton(){
        ImageView userSettingsLink = MainActivity.this.findViewById(R.id.MainActivityImageViewSettingsPage);
        userSettingsLink.setOnClickListener(v -> {
            Intent goToSettingsPage = new Intent(this, SettingsPage.class);
            startActivity(goToSettingsPage);
        });
    }

    public void setupUsernameDisplay(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(SettingsPage.USERNAME_TAG, "No Username");
        ((TextView)findViewById(R.id.MainActivityTextViewHomeTitle)).setText(username + "'s Tasks");
    }

    public void setupTaskTextViewLinks() {
        TextView createSettingsPageTask = findViewById(R.id.MainActivityTextViewCreateSettingsTask);
        TextView createTaskDetailPageTask = findViewById(R.id.MainActivityTextViewCreateTaskDetailPageTask);
        TextView displayUsernameTask = findViewById(R.id.MainActivityTextViewDisplayUsernameTask);
        Intent goToTaskDetailIntent = new Intent(this, TaskDetailPage.class);
        createSettingsPageTask.setOnClickListener(v -> {
            goToTaskDetailIntent.putExtra(MY_TASK_EXTRA_TAG, createSettingsPageTask.getText().toString());
            startActivity(goToTaskDetailIntent);
        });

        createTaskDetailPageTask.setOnClickListener(v -> {
            goToTaskDetailIntent.putExtra(MY_TASK_EXTRA_TAG, createTaskDetailPageTask.getText().toString());
            startActivity(goToTaskDetailIntent);
        });

        displayUsernameTask.setOnClickListener(v -> {
            goToTaskDetailIntent.putExtra(MY_TASK_EXTRA_TAG, displayUsernameTask.getText().toString());
            startActivity(goToTaskDetailIntent);
        });
    }
}