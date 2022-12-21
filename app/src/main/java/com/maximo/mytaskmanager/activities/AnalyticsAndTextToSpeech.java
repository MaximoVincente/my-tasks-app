package com.maximo.mytaskmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.amplifyframework.analytics.AnalyticsEvent;
import com.amplifyframework.core.Amplify;
import com.maximo.mytaskmanager.R;

import java.util.Date;

public class AnalyticsAndTextToSpeech extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_and_text_to_speech);

        AnalyticsEvent event = AnalyticsEvent.builder()
                .name("Opened Analytics Activity")
                .addProperty("Time", Long.toString(new Date().getTime()))
                .addProperty("Tracking Event", "Analytics activity was opened")
                .build();

        Amplify.Analytics.recordEvent(event);
    }
}