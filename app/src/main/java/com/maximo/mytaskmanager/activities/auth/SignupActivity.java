package com.maximo.mytaskmanager.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.maximo.mytaskmanager.R;

public class SignupActivity extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    public static final String SIGNUP_EMAIL_TAG = "Signup_Email_Tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        setUpSignUpForm();
    }

    public void setUpSignUpForm(){
        findViewById(R.id.SignInActivityButtonSignup).setOnClickListener(v -> {
            String userEmail = ((EditText) findViewById(R.id.SignupActivityEditTextTextEmailAddress)).getText().toString();
            String userPassword = ((EditText) findViewById(R.id.SignupActivityEditTextPassword)).getText().toString();
            Amplify.Auth.signUp(
                userEmail,
                userPassword,
                AuthSignUpOptions.builder()
                    .userAttribute(AuthUserAttributeKey.email(), userEmail)
                    .build(),
                    success -> {
                    Log.i(TAG, "SignUp success! " + success);
                    Intent goToVerifyActivity = new Intent(this, VerifySignupActivity.class);
                    goToVerifyActivity.putExtra(SIGNUP_EMAIL_TAG, userEmail);
                    startActivity(goToVerifyActivity);
                    },
                    failure -> {
                        Log.w(TAG, "Sign up failed with username: " + userEmail + "with message " + failure);
                        runOnUiThread(() -> Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show());
                    }
            );
        });
    }
}