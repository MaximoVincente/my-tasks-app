package com.maximo.mytaskmanager.activities;

//import static com.maximo.mytaskmanager.models.Task.State.Assigned;
//import static com.maximo.mytaskmanager.models.Task.State.Complete;
//import static com.maximo.mytaskmanager.models.Task.State.InProgress;
//import static com.maximo.mytaskmanager.models.Task.State.New;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.adapter.TaskRecyclerViewAdapter;
import com.maximo.mytaskmanager.database.MyTaskManagerDatabase;
import com.maximo.mytaskmanager.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    MyTaskManagerDatabase myTaskManagerDatabase;

    public static final String DATABASE_NAME = "myTaskDatabase";
    public static final String TASK_TITLE_TAG = "taskTitle";
    public static final String TASK_TITLE_DESCRIPTION = "taskDescription";
    public static final String TASK_TITLE_STATE = "taskState";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTaskManagerDatabase = Room.databaseBuilder(
                getApplicationContext(),
                MyTaskManagerDatabase.class,
                DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        setupAddTaskButton();
        setupAllTasksButton();
        setupSettingsPageImageButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUsernameDisplay();
        setupTasksRecyclerView();
    }

    public void setupTasksRecyclerView() {
        List<Task> tasks = myTaskManagerDatabase.taskDao().findAll();
//        tasks.add(new Task("Hike with Wally", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo.", New));
//        tasks.add(new Task("Do Lab 100", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh. Id eu nisl nunc mi ipsum faucibus vitae. ", InProgress));
//        tasks.add(new Task("Do Code Challenge 47", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ", New));
//        tasks.add(new Task("Grab Ski Gear", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. ", InProgress));
//        tasks.add(new Task("Oil change to my car", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo.", New));
//        tasks.add(new Task("Exercise before class", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh.", Assigned));
//        tasks.add(new Task("Email Square Recruiter", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. ", Complete));
//        tasks.add(new Task("Email Comcast Recruiter", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh. Id eu nisl nunc mi ipsum faucibus vitae. ", Complete));
//        tasks.add(new Task("Apply to saved job positions", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh. Id eu nisl nunc mi ipsum faucibus vitae. ", Complete));
//        tasks.add(new Task("Check MEDPROS", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh. Id eu nisl nunc mi ipsum faucibus vitae. ", Assigned));
//        tasks.add(new Task("Do Cyber Awareness Refresher", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh.", InProgress));
//        tasks.add(new Task("Submit expected salary for job offer", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Fames ac turpis egestas maecenas pharetra convallis. Est placerat in egestas erat imperdiet. Iaculis at erat pellentesque adipiscing commodo. In est ante in nibh. Id eu nisl nunc mi ipsum faucibus vitae. In arcu cursus euismod quis viverra nibh cras pulvinar. Pellentesque id nibh tortor id aliquet lectus. Ut tortor pretium viverra suspendisse potenti nullam ac tortor. Pretium viverra suspendisse potenti nullam. Dolor purus non enim praesent.", New));
        RecyclerView tasksRecyclerView = findViewById(R.id.MainActivityRecyclerViewTasks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(layoutManager);
        TaskRecyclerViewAdapter adapter = new TaskRecyclerViewAdapter(tasks, this);
        tasksRecyclerView.setAdapter(adapter);
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
}