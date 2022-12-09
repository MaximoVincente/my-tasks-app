package com.maximo.mytaskmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.maximo.mytaskmanager.R;


public class TaskDetailPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail_page);
        taskExtra();
    }

    public void taskExtra(){
        Intent callingIntent = getIntent();
        String taskTitle = null;
        String taskDescription= null;
        String taskState = null;
        if(callingIntent != null){
            taskTitle = callingIntent.getStringExtra(MainActivity.TASK_TITLE_TAG);
            taskDescription = callingIntent.getStringExtra(MainActivity.TASK_TITLE_DESCRIPTION);
            taskState = callingIntent.getStringExtra(MainActivity.TASK_TITLE_STATE);
        }
        TextView taskDetailTitleView = findViewById(R.id.ActivityTaskDetailPageTextViewTaskTitle);
        TextView taskDetailDescriptionView = findViewById(R.id.ActivityTaskDetailTextViewTaskDescription);
        Spinner taskDetailState = findViewById(R.id.ActivityTaskDetailSpinnerTaskState);
        if(taskTitle != null) {
            taskDetailTitleView.setText(taskTitle);
            taskDetailDescriptionView.setText(taskDescription);
            taskDetailState.setAdapter(new ArrayAdapter<>(
                    taskDetailState.getContext(),
                    android.R.layout.simple_spinner_item,
                    TaskStateEnum.values()
            ));
        }
        else {
            taskDetailTitleView.setText("No Tasks");
            taskDetailDescriptionView.setText("No Description");
        }
    }

}