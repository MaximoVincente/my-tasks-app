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

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.maximo.mytaskmanager.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTask extends AppCompatActivity {
    public final static String TAG = "AddTaskActivity";
    Spinner taskStateSpinner;
    Spinner teamSpinner;
    CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

//        Team newTeam1 = Team.builder()
//                .teamName("Vesper")
//                .build();
//        Team newTeam2 = Team.builder()
//                .teamName("OfferHub")
//                .build();
//        Team newTeam3 = Team.builder()
//                .teamName("Android")
//                .build();
//        Amplify.API.mutate(
//            ModelMutation.create(newTeam1),
//            success -> {},
//            failure -> {}
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(newTeam2),
//                success -> {},
//                failure -> {}
//        );
//        Amplify.API.mutate(
//                ModelMutation.create(newTeam3),
//                success -> {},
//                failure -> {}
//        );

        taskStateSpinner = findViewById(R.id.AddTaskActivitySpinnerState);
        teamSpinner = findViewById(R.id.AddTaskActivitySpinnerAddTeam);

        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read Teams Successfully");
                    ArrayList<String> teamNames = new ArrayList<>();
                    ArrayList<Team> teams = new ArrayList<>();
                    for (Team team: success.getData()) {
                        teamNames.add(team.getTeamName());
                        teams.add(team);
                    }
                    teamsFuture.complete(teams);
                    runOnUiThread(() -> {
                        setupTeamSpinner(teamNames);
                    });
                },
                failure -> {
                    teamsFuture.complete(null);
                    Log.w(TAG, "Failed to read Teams from Database");
                }
        );
            setupTypeSpinner();
            setupAddTaskButton();
    }

    public void setupTeamSpinner(ArrayList<String> teamNames){
        teamSpinner.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                teamNames
        ));
    }

    public void setupTypeSpinner(){
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                taskStateSpinner.getContext(),
                android.R.layout.simple_spinner_item,
                TaskStateEnum.values()
        ));
    }

    public void setupAddTaskButton(){
        Button addTaskButton = findViewById(R.id.AddTaskActivitySubmitTaskButton);
        addTaskButton.setOnClickListener(view -> {
            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            List<Team> teams = null;
            try{
                teams = teamsFuture.get();
            } catch(InterruptedException ie){
                Log.e(TAG, "InterruptedException while getting teams");
                Thread.currentThread().interrupt();
            } catch(ExecutionException ee) {
                Log.e(TAG, "ExecutionException while getting teams");
            }
            Team selectedTeam = teams.stream().filter(theTeam -> theTeam.getTeamName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);
            Task newTask = Task.builder()
                .taskTitle(((EditText)findViewById(R.id.AddTaskActivityEditTextTaskTitle)).getText().toString())
                .taskDescription(((EditText)findViewById(R.id.AddTaskActivityEditTextTaskDescription)).getText().toString())

                .state((TaskStateEnum)taskStateSpinner.getSelectedItem())
                    .team(selectedTeam)
                .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "AddTaskActivity.onCreate(): made a task successfully!"),
                    failure -> Log.w(TAG, "AddTaskActivity.onCreate(): failed to make a task", failure)
            );

            Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}


