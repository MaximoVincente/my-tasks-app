package com.maximo.mytaskmanager.activities;


import static com.amplifyframework.core.Amplify.Auth;
import static com.maximo.mytaskmanager.activities.SettingsPage.TEAM_TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.cognito.result.AWSCognitoAuthSignOutResult;
import com.amplifyframework.auth.cognito.result.GlobalSignOutError;
import com.amplifyframework.auth.cognito.result.HostedUIError;
import com.amplifyframework.auth.cognito.result.RevokeTokenError;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.activities.auth.SignInActivity;
import com.maximo.mytaskmanager.activities.auth.SignupActivity;
import com.maximo.mytaskmanager.adapter.TaskRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_NAME = "myTasksAppDatabase";
    public static final String TAG = "mainActivity";
    public static final String TASK_TITLE_TAG = "taskTitle";
    public static final String TASK_TITLE_DESCRIPTION = "taskDescription";
    public static final String TASK_TITLE_STATE = "taskState";
    public static final String TASK_IMAGE = "taskImage";
    SharedPreferences preferences;
    TaskRecyclerViewAdapter adapter;
    public AuthUser authUser = null;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setupAddTaskButton();
        setupAllTasksButton();
        setupSettingsPageImageButton();
        setupTasksRecyclerView();
        setupSignUpSignIn();
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


    public void setupSignUpSignIn(){
        Button signIn = MainActivity.this.findViewById(R.id.MainActivityButtonLoginButton);
        Button signUp = MainActivity.this.findViewById(R.id.MainActivityButtonSignupButton);
        signIn.setOnClickListener(view -> {
            Intent goToSignInActivity = new Intent(this, SignInActivity.class);
            startActivity(goToSignInActivity);
        });

        signUp.setOnClickListener(view -> {
            Intent goToSignupActivity = new Intent(this, SignupActivity.class);
            startActivity(goToSignupActivity);
        });

        Button signOutButton = this.findViewById(R.id.MainActivityButtonLogoutButton);
        signOutButton.setOnClickListener(view ->

                Auth.signOut( signOutResult -> {
                    if (signOutResult instanceof AWSCognitoAuthSignOutResult.CompleteSignOut) {
                        // Sign Out completed fully and without errors.
                        Log.i("AuthQuickStart", "Signed out successfully");
                        startActivity(new Intent(this, MainActivity.class));
                    } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.PartialSignOut) {
                        // Sign Out completed with some errors. User is signed out of the device.
                        AWSCognitoAuthSignOutResult.PartialSignOut partialSignOutResult =
                                (AWSCognitoAuthSignOutResult.PartialSignOut) signOutResult;

                        HostedUIError hostedUIError = partialSignOutResult.getHostedUIError();
                        if (hostedUIError != null) {
                            Log.e("AuthQuickStart", "HostedUI Error", hostedUIError.getException());
                            // Optional: Re-launch hostedUIError.getUrl() in a Custom tab to clear Cognito web session.
                        }

                        GlobalSignOutError globalSignOutError = partialSignOutResult.getGlobalSignOutError();
                        if (globalSignOutError != null) {
                            Log.e("AuthQuickStart", "GlobalSignOut Error", globalSignOutError.getException());
                            // Optional: Use escape hatch to retry revocation of globalSignOutError.getAccessToken().
                        }

                        RevokeTokenError revokeTokenError = partialSignOutResult.getRevokeTokenError();
                        if (revokeTokenError != null) {
                            Log.e("AuthQuickStart", "RevokeToken Error", revokeTokenError.getException());
                            // Optional: Use escape hatch to retry revocation of revokeTokenError.getRefreshToken().
                        }
                    } else if (signOutResult instanceof AWSCognitoAuthSignOutResult.FailedSignOut) {
                        AWSCognitoAuthSignOutResult.FailedSignOut failedSignOutResult =
                                (AWSCognitoAuthSignOutResult.FailedSignOut) signOutResult;
                        // Sign Out failed with an exception, leaving the user signed in.
                        Log.e("AuthQuickStart", "Sign out Failed", failedSignOutResult.getException());
                    }
                })
        );

        Amplify.Auth.getCurrentUser(
                success->{
                    String username = success.getUsername();
                    Log.i(TAG, "Username is: " + username);
                    signIn.setVisibility(View.GONE);
                    signUp.setVisibility(View.GONE);
                },
                failure->{
                    signOutButton.setVisibility(View.GONE);
                }
        );
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