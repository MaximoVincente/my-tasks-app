package com.maximo.mytaskmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.maximo.mytaskmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SettingsPage extends AppCompatActivity {
    SharedPreferences preferences;
    public static final String USERNAME_TAG = "username";
    public static final String TEAM_TAG = "teamName";
    public static final String TAG = "settings_page";
    Spinner teamSpinner;
    CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveUserSettingsValuesToSharedPrefs();
    }

    public void saveUserSettingsValuesToSharedPrefs(){
        // Setup the editor -> sharedPrefs is read only by default
        String usernameString = preferences.getString(USERNAME_TAG, "");
        String teamString = preferences.getString(TEAM_TAG, "");
        teamsFuture = new CompletableFuture<>();
        List<Team> teamList = new ArrayList<>();
        List<String> teamListAsString = new ArrayList<>();
        teamSpinner = findViewById(R.id.SettingsPageActivitySpinnerAddTeam);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read products successfully");
                    for (Team team : success.getData()) {
                        teamList.add(team);
                    }
                    teamsFuture.complete(teamList);
                    runOnUiThread(() -> {
                        teamListAsString.add("All");
                        for (Team team : teamList)
                            teamListAsString.add(team.getTeamName());
                        teamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                android.R.layout.simple_spinner_item,
                                teamListAsString
                        ));
                        if (!teamString.isEmpty()) {
                            teamSpinner.setSelection(teamListAsString.indexOf(teamString));
                        }
                    });
                },
                failure -> Log.i(TAG, "Failed to read products")
        );
        if (!usernameString.isEmpty()) {
            EditText usernameEditText = findViewById(R.id.ActivitySettingsPageEditTextUsername);
            usernameEditText.setText(usernameString);
        }
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        Button saveButton = SettingsPage.this.findViewById(R.id.ActivitySettingsPageButtonSave);
        saveButton.setOnClickListener(v -> {
            EditText usernameText = findViewById(R.id.ActivitySettingsPageEditTextUsername);
            String usernameStrin = usernameText.getText().toString();
            String teamStrin = teamSpinner.getSelectedItem().toString();
            preferenceEditor.putString(USERNAME_TAG, usernameStrin);
            preferenceEditor.putString(TEAM_TAG, teamStrin);
            preferenceEditor.apply();

            Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();
        });
    }

}