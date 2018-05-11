package com.example.exoplayer.exoplayer;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.example.exoplayer.Glide.GlideApp;
import com.example.exoplayer.Glide.GlideThumbnailTransformation;
import com.example.exoplayer.exoplayer.ExoPlayerMediaSourceBuilder;
import com.github.rubensousa.previewseekbar.base.PreviewLoader;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by arun.r on 10-05-2018.
 */

public class ExoPlayerManager implements PreviewLoader {

    private ExoPlayerMediaSourceBuilder mediaSourceBuilder;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private String thumbnailsUrl;
    private ImageView imageView;
    public static long realDurationMillis;
    private boolean durationSet;
    private Player.EventListener eventListener = new Player.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY && playWhenReady && !durationSet) {
                previewTimeBarLayout.hidePreview();
                realDurationMillis = player.getDuration();
                durationSet = true;
            }
        }
    };

    ArrayList<String> imageSources;

    public void setImageSources(ArrayList<String> imageSources) {
        this.imageSources = imageSources;
    }

    public long getRealDurationMillis() {
        return realDurationMillis;
    }

    public ExoPlayerManager() {

    }

    public ExoPlayerManager(SimpleExoPlayerView playerView,
                            PreviewTimeBarLayout previewTimeBarLayout, ImageView imageView,
                            String thumbnailsUrl) {
        this.playerView = playerView;
        this.imageView = imageView;
        this.previewTimeBarLayout = previewTimeBarLayout;
        this.mediaSourceBuilder = new ExoPlayerMediaSourceBuilder(playerView.getContext());
        this.thumbnailsUrl = thumbnailsUrl;
    }

    public void play(Uri uri) {
        mediaSourceBuilder.setUri(uri);
    }

    public void onStart() {
        if (Util.SDK_INT > 23) {
            createPlayers();
        }
    }

    public void onResume() {
        if (Util.SDK_INT <= 23) {
            createPlayers();
        }
    }

    public void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayers();
        }
    }

    public void onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayers();
        }
    }

    public void stopPreview() {
        player.setPlayWhenReady(true);
    }

    private void releasePlayers() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void createPlayers() {
        if (player != null) {
            player.release();
        }
        player = createFullPlayer();
        playerView.setPlayer(player);
    }

    private SimpleExoPlayer createFullPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory
                = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(playerView.getContext()),
                trackSelector, loadControl);
        player.setPlayWhenReady(true);
        player.prepare(mediaSourceBuilder.getMediaSource(false));
        player.addListener(eventListener);
        return player;
    }

    @Override
    public void loadPreview(long currentPosition, long max) {
        player.setPlayWhenReady(false);
        GlideApp.with(imageView)
                .load(thumbnailsUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new GlideThumbnailTransformation(currentPosition))
                .into(imageView);
    }
}
