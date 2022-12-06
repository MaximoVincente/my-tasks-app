package com.maximo.mytaskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.maximo.mytaskmanager.R;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);
        setupGoBackButton();
    }

    public void setupGoBackButton(){
        Button addToTaskButton = AllTasks.this.findViewById(R.id.AllTasksGoBackButton);
        addToTaskButton.setOnClickListener(view -> {
            Intent goBack = new Intent(this, MainActivity.class);
            startActivity(goBack);
        });
    }
}
