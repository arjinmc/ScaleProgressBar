package com.arjinmc.scaleprogressbar;

import com.arjinmc.widgets.ScaleProgressBar;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

/**
 * Main
 * @description 
 * @author Eminem Lu
 * @email arjinmc@hotmail.com
 * @date 2015/6/17
 */
public class MainActivity extends Activity {

	private ScaleProgressBar spBar;
	
	/**
	 * this is to sumulate a progress loading
	 */
	private CountDownTimer timer = new CountDownTimer(10000,100) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			mHandler.sendEmptyMessage(100-(int)millisUntilFinished/100);
		}
		
		@Override
		public void onFinish() {
			mHandler.sendEmptyMessage(spBar.MAX_PROGRESS);
		}
	};
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			spBar.setProgress(msg.what);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		spBar = (ScaleProgressBar) findViewById(R.id.spb);
		timer.start();
	}
}
