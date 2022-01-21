package com.example.healthwithme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class StartStretchingExercisePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stretching_exercise_page);

        VideoView videoView = findViewById(R.id.videoView);

        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.stretching);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }
}