package com.maximo.mytaskmanager.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskStateEnum;
import com.amplifyframework.datastore.generated.model.Team;
import com.maximo.mytaskmanager.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
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
    ActivityResultLauncher<Intent> activityResultLauncher; // at top of class
    private String s3ImageKey = "";

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
        activityResultLauncher = getImagePickingActivityResultLauncher();
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

            setupAddImageBttn();
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

    private void setupAddImageBttn(){
        findViewById(R.id.AddTaskActivityImageViewAddImage).setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
    }


    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});

        activityResultLauncher.launch(imageFilePickingIntent);
    }

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher(){
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher =
                registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            Uri pickedImageFileUri = result.getData().getData();
                            try{
                                // take in the file URI and turn it into a inputStream
                                InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
//              String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
                                String pickedImageFilename = DocumentFile.fromSingleUri(this, pickedImageFileUri).getName();
                                Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
                            } catch (FileNotFoundException fnfe){
                                Log.e(TAG, "Could not get file from file picker" + fnfe.getMessage());
                            }
                        }
                );
        return imagePickingActivityResultLauncher;
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFilename, Uri pickedImageFileUri){
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in getting file uploaded to S3! Key is: " + success.getKey());
                    s3ImageKey = success.getKey();
                    // TODO:
//          saveSuperPet();
                    ImageView taskImage = (ImageView) findViewById(R.id.AddTaskActivityImageViewAddImage);
                    InputStream pickedImageInputStreamCopy = null; // need to make a copy because InputStreams cannot be reused!
                    try {
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageFileUri);
                    } catch (FileNotFoundException fnfe) {
                        Log.e(TAG, "Could not get file stream from URI! " + fnfe.getMessage(), fnfe);
                    }
                    taskImage.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy));
                },
                failure -> Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage())
        );
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
                    .s3ImageKey(s3ImageKey)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "AddTaskActivity.onCreate(): made a task successfully!"),
                    failure -> Log.w(TAG, "AddTaskActivity.onCreate(): failed to make a task", failure)
            );

            Toast.makeText(this, "Task Saved!", Toast.LENGTH_SHORT).show();
        });
    }

//    @SuppressLint("Range")
//    public String getFileNameFromUri(Uri uri) {
//        String result = null;
//        if (uri.getScheme().equals("content")) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        if (result == null) {
//            result = uri.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut + 1);
//            }
//        }
//        return result;
//    }
}


