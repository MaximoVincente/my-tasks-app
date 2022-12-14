package com.maximo.mytaskmanager.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.maximo.mytaskmanager.R;

public class VerifySignupActivity extends AppCompatActivity {
    public static final String TAG = "VerifyAccountActivity";
    Intent callingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_signup);
        callingIntent = getIntent();

        setUpVerifyForm();
    }

    public void setUpVerifyForm(){
        String userEmail = callingIntent.getStringExtra(SignupActivity.SIGNUP_EMAIL_TAG);
        findViewById(R.id.VerifySignupActivityButtonConfirmationCodeButton).setOnClickListener(v -> {
            String verificationCode = ((EditText) findViewById(R.id.VerifySignupActivityEditTextConfirmationCode)).getText().toString();
            Amplify.Auth.confirmSignUp(
                    userEmail,
                    verificationCode,
                    success -> {
                        Log.i(TAG, "Verification succeeded: " + success.toString());
                        Intent goToSignInActivity = new Intent(this, SignInActivity.class);
                        goToSignInActivity.putExtra(SignupActivity.SIGNUP_EMAIL_TAG, userEmail);
                        startActivity(goToSignInActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Verification failed with username: " + userEmail + " with this message: " + failure);
                        runOnUiThread(() -> Toast.makeText(VerifySignupActivity.this, "Verify account failed!", Toast.LENGTH_SHORT));
                    }
            );
        });
    }

}