package com.arjinmc.scaleprogressbar;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.arjinmc.widgets.ScaleProgressDialog;

/**
 * Main
 * @description 
 * @author Eminem Lu
 * @email arjinmc@hotmail.com
 * @date 2015/6/17
 */
public class MainActivity extends Activity {

	private ScaleProgressDialog spDialog;
	private Button btn;
	
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
			mHandler.sendEmptyMessage(spDialog.MAX_PROGRESS);
		}
	};
	
	private Handler mHandler = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
			spDialog.setProgress(msg.what);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				spDialog.show();
				timer.start();
			}
		});
		
		spDialog = new ScaleProgressDialog(this);
		spDialog.show();
		timer.start();
	}
}
