package com.maximo.mytaskmanager.activities;


import static com.maximo.mytaskmanager.activities.SettingsPage.TEAM_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "myTasksAppDatabase";
    public static final String TAG = "mainActivity";
    public static final String TASK_TITLE_TAG = "taskTitle";
    public static final String TASK_TITLE_DESCRIPTION = "taskDescription";
    public static final String TASK_TITLE_STATE = "taskState";
    SharedPreferences preferences;
    TaskRecyclerViewAdapter adapter;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AuthO Signup Setup
        Amplify.Auth.signUp(
            "mavincen",
            "password",
            AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.preferredUsername(), "mavincen")
            .userAttribute(AuthUserAttributeKey.email(), "maximovincente@gmail.com")
            .userAttribute(AuthUserAttributeKey.nickname(), "max")
            .build(),
            success -> Log.i(TAG, "Signup Successful" + success.toString()),
            failure -> Log.w(TAG, "Signup Failed with username " + "mavincen" + "with the message: " + failure )
        );

        //AuthO Verification
        Amplify.Auth.confirmSignUp(
                "maximovincente@gmail.com",
                "123456",
                success ->  Log.i (TAG, "Verification Successful"),
                failure -> Log.w(TAG, "Verification Failed ")
        );

        //AuthO Login
        Amplify.Auth.signIn(
                "mavincen",
                "password",
                success -> {},
                failure -> {}
        );
        //Auth0 Logout
        Amplify.Auth.signOut(
                "mavincen",
                success -> Log.i (TAG, "Logout Successful"),
                failure -> Log.w (TAG, "Logout Failed")
        );

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setupAddTaskButton();
        setupAllTasksButton();
        setupSettingsPageImageButton();
        setupTasksRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentTeam = preferences.getString(TEAM_TAG, "All");
        tasks.clear();
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Log.i(DATABASE_NAME, "Read tasks successfully");
                    for (Task task : success.getData()) {
                        if (currentTeam.equals("All") || task.getTeam().getTeamName().equals(currentTeam))
                            tasks.add(task);
                    }
                    runOnUiThread(() -> adapter.notifyDataSetChanged()); // since this runs asynchronously, the adapter may already have rendered, so we have to tell it to update
                },
                failure -> Log.e(DATABASE_NAME, "Failed to read Tasks from database")
        );
        setupUsernameDisplay();
    }

    public void setupTasksRecyclerView() {
        tasks = new ArrayList<>();
        RecyclerView tasksRecyclerView = findViewById(R.id.MainActivityRecyclerViewTasks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        tasksRecyclerView.setLayoutManager(layoutManager);
        adapter = new TaskRecyclerViewAdapter(tasks, this);
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

        String username = preferences.getString(SettingsPage.USERNAME_TAG, "No Username");
        ((TextView)findViewById(R.id.MainActivityTextViewHomeTitle)).setText(username + "'s Tasks");
    }
}