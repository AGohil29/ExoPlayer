/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
* limitations under the License.
 */
package com.example.exoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.exoplayer.exoplayer.ExoPlayerManager;
import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.ArrayList;

/**
 * A fullscreen activity to play audio or video streams.
 */
public class PlayerActivity extends AppCompatActivity implements PreviewView.OnPreviewChangeListener {

    private static final int PICK_FILE_REQUEST_CODE = 2;

    private ExoPlayerManager exoPlayerManager;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;
    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        SimpleExoPlayerView playerView = findViewById(R.id.playerView);

        previewTimeBar = (PreviewTimeBar) findViewById(R.id.exo_progress);
        previewTimeBarLayout = findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorAccent);
        previewTimeBar.addOnPreviewChangeListener(this);
        exoPlayerManager = new ExoPlayerManager(playerView, previewTimeBarLayout,
                (ImageView) findViewById(R.id.imageView), getString(R.string.url_thumbnails));
        exoPlayerManager.play(Uri.parse(getString(R.string.url_smooth)));
        exoPlayerManager.setImageSources(images);
        previewTimeBarLayout.setPreviewLoader(exoPlayerManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            exoPlayerManager.play(data.getData());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        exoPlayerManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayerManager.onStop();
    }

    @Override
    public void onStartPreview(PreviewView previewView) {

    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        exoPlayerManager.stopPreview();
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {

    }

}
