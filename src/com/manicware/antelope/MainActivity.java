package com.manicware.antelope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
	final String SUGGESTED_URL = "http://www.vorbis.com/music/Epoq-Lepidoptera.ogg";
	
	private Button mPlayButton;
	private Button mPrevButton;
	private Button mNextButton;
	private ToggleButton mRepeatToggle;
	private ToggleButton mShuffleToggle;
	
	private boolean playing = false;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mPlayButton = (Button)findViewById(R.id.playButton);
        mPrevButton = (Button)findViewById(R.id.prevButton);
        mNextButton = (Button)findViewById(R.id.nextButton);
        mRepeatToggle = (ToggleButton)findViewById(R.id.repeatToggle);
        mShuffleToggle = (ToggleButton)findViewById(R.id.shuffleToggle);
        
        mPlayButton.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mRepeatToggle.setOnCheckedChangeListener(this);
        mShuffleToggle.setOnCheckedChangeListener(this);
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		if (v == mPlayButton) {
			if (!playing){
				startService(new Intent(MusicService.ACTION_PLAY));
				playing = true;
				mPlayButton.setText(R.string.pause_button);
			} else {
				startService(new Intent(MusicService.ACTION_PAUSE));
				playing = false;
				mPlayButton.setText(R.string.play_button);
			}
		} else if (v == mPrevButton) {
			startService(new Intent(MusicService.ACTION_REWIND));
		} else if (v == mNextButton) {
			startService(new Intent(MusicService.ACTION_SKIP));
		}
	}
	
    void showUrlDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Manual Input");
        alertBuilder.setMessage("Enter a URL (must be http://)");
        final EditText input = new EditText(this);
        alertBuilder.setView(input);

        input.setText(SUGGESTED_URL);

        alertBuilder.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
                // Send an intent with the URL of the song to play. This is expected by
                // MusicService.
                Intent i = new Intent(MusicService.ACTION_URL);
                Uri uri = Uri.parse(input.getText().toString());
                i.setData(uri);
                startService(i);
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {}
        });

        alertBuilder.show();
    }
}