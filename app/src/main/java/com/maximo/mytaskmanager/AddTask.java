package com.maximo.mytaskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button submitTaskButton = AddTask.this.findViewById(R.id.AddTaskActivitySubmitTaskButton);

        submitTaskButton.setOnClickListener(view -> {
            Log.v("", "Very Verbose");
            Log.d("", "Debug");
            Log.i("", "Information");
            Log.w("", "Warning");
            Log.e("", "Error");
            Log.wtf("", "What a terrible failure");
            TextView greeting = AddTask.this.findViewById(R.id.AddTaskActivityTextViewSubmittedMessage);
            greeting.setText("Submitted");
        });
    }

}
