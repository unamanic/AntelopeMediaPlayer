/** Antelope Media Player - Media player for Android
  * Copyright (C) 2011  William C. Witt
  * 
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published by
  * the Free Software Foundation, either version 3 of the License, or
  * (at your option) any later version.
  * 
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  * 
  * You should have received a copy of the GNU General Public License
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  */

package com.manicware.antelope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
    private static final int ABOUT_ID = Menu.FIRST;
	
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
	
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, ABOUT_ID, 0, R.string.about_menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case ABOUT_ID:
            aboutShow();
            return true;
        }
        
        return super.onMenuItemSelected(featureId, item);
    }
    
    private void aboutShow() {
    	Intent i = new Intent(this, About.class);
        startActivity(i);
    }
}