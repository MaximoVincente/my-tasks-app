package com.maximo.mytaskmanager.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.maximo.mytaskmanager.R;
import com.maximo.mytaskmanager.activities.MainActivity;

public class SignInActivity extends AppCompatActivity {
    public static final String TAG = "signInActivity";
    Intent callingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        callingIntent = getIntent();
        setUpSignInForm();
    }

    public void setUpSignInForm(){

        findViewById(R.id.SignInActivityButtonSignup).setOnClickListener(v -> {
            String userEmail = ((EditText) findViewById(R.id.SignInActivityEditTextTextEmailAddress)).getText().toString();
            String userPassword = ((EditText) findViewById(R.id.SignInActivityEditTextPassword)).getText().toString();

            // make a login call to Cognito
            Amplify.Auth.signIn(
                    userEmail,
                    userPassword,
                    success -> {
                        Log.i(TAG, "Login successful: " + success);
                        Intent goToProductListIntent = new Intent(this, MainActivity.class);
                        startActivity(goToProductListIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Login failed: " + failure);
                        runOnUiThread(() -> Toast.makeText(this, "Sign In failed!", Toast.LENGTH_SHORT).show());
                    }
            );
        });
    }

}