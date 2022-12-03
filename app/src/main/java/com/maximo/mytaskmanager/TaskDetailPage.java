package com.maximo.mytaskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
        if(callingIntent != null){
            taskTitle = callingIntent.getStringExtra(MainActivity.MY_TASK_EXTRA_TAG);
        }
        TextView taskDetailTitleView = findViewById(R.id.ActivityTaskDetailPageTextViewTaskTitle);
        if(taskTitle != null) {
            taskDetailTitleView.setText(taskTitle);
        }
        else {
            taskDetailTitleView.setText("No Tasks");
        }
    }

}