package com.example.exoplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.SeekBar;

import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity;

/**
 * Created by arun.r on 30-04-2018.
 */

public class ExpandedControlsActivity extends ExpandedControllerActivity {
    private SeekBar seekBar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        //seekBar = (SeekBar) this.getSeekBar();
    }

    /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        int currDur = seekBar.getProgress();
        intent.putExtra("currTime", currDur);
        setResult(200, intent);
        finish();
    }*/
}
