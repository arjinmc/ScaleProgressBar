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
 *
 * @author Eminem Loo
 * @description
 * @email arjinmc@hotmail.com
 * @date 2015/6/17
 */
public class MainActivity extends Activity {

    private ScaleProgressDialog mScaleProgressDialog;
    private Button mBtn;

    /**
     * this is to sumulate a progress loading
     */
    private CountDownTimer timer = new CountDownTimer(10000, 100) {

        @Override
        public void onTick(long millisUntilFinished) {
            mHandler.sendEmptyMessage(100 - (int) millisUntilFinished / 100);
        }

        @Override
        public void onFinish() {
            mHandler.sendEmptyMessage(mScaleProgressDialog.MAX_PROGRESS);
        }
    };

    private Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            mScaleProgressDialog.setProgress(msg.what);
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mScaleProgressDialog.show();
                timer.start();
            }
        });

        mScaleProgressDialog = new ScaleProgressDialog(this);
        mScaleProgressDialog.show();
        timer.start();
    }
}
