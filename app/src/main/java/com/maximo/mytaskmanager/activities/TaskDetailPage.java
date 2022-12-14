package com.maximo.mytaskmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.maximo.mytaskmanager.R;

import java.io.File;
import java.net.URI;


public class TaskDetailPage extends AppCompatActivity {
    public static final String TAG = "taskDetailActivity";

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
        String taskImageTag = null;
        String taskLocation = null;
        if(callingIntent != null){
            taskTitle = callingIntent.getStringExtra(MainActivity.TASK_TITLE_TAG);
            taskDescription = callingIntent.getStringExtra(MainActivity.TASK_TITLE_DESCRIPTION);
//            taskImageTag = callingIntent.getStringExtra(MainActivity.TASK_IMAGE).split("/")[1];
            taskLocation = callingIntent.getStringExtra(LocationActivity.TASK_LOCATION);

        }
        TextView taskDetailTitleView = findViewById(R.id.ActivityTaskDetailPageTextViewTaskTitle);
        TextView taskDetailDescriptionView = findViewById(R.id.ActivityTaskDetailTextViewTaskDescription);
        Spinner taskDetailState = findViewById(R.id.ActivityTaskDetailSpinnerTaskState);
        ImageView taskImageV = (ImageView) findViewById(R.id.ActivityTaskDetailPageImageViewTaskImage);
        TextView taskDetailLocation = findViewById(R.id.LocationActivityTextViewLocation);

        if(taskTitle != null) {
            taskDetailTitleView.setText(taskTitle);
            taskDetailDescriptionView.setText(taskDescription);
            taskDetailState.setAdapter(new ArrayAdapter<>(
                    taskDetailState.getContext(),
                    android.R.layout.simple_spinner_item,
                    TaskStateEnum.values()
            ));
            taskDetailLocation.setText(taskLocation);
//            taskImage = new File(getApplicationContext().getFilesDir() + "/" + taskImageTag);
//            Amplify.Storage.downloadFile(
//                    taskImageTag,
//                    taskImageV,
//                    success -> {
//                        Log.i(TAG, "Download Successful for " + success.getFile());
//                        taskImage
//                    }
//            )

        }
        else {
            taskDetailTitleView.setText("No Tasks");
            taskDetailDescriptionView.setText("No Description");
        }
    }

}