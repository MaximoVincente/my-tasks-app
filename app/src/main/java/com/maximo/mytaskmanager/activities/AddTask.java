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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.maximo.mytaskmanager.R;

import java.util.Date;

public class AddTask extends AppCompatActivity {
    public final static String TAG = "AddTaskActivity";
    Spinner taskStateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskStateSpinner = findViewById(R.id.AddTaskActivitySpinnerState);
            setupTypeSpinner();
            setupAddTaskButton();

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
            Task newTask = Task.builder()
                .taskTitle(((EditText)findViewById(R.id.AddTaskActivityEditTextTaskTitle)).getText().toString())
                .taskDescription(((EditText)findViewById(R.id.AddTaskActivityEditTextTaskDescription)).getText().toString())
                .state((TaskStateEnum)taskStateSpinner.getSelectedItem())
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


