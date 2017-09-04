package com.arjinmc.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

/**
 * ScaleProgressDialog
 *
 * @author Eminem Loo
 * @description
 * @email arjinmc@hotmail.com
 * @date 2015/6/18
 */
public class ScaleProgressDialog extends AlertDialog implements ScaleProgressBar.OnAnimationFinishListener {

    private Context mContext;
    private ScaleProgressBar mScaleProgressBar;
    public int MAX_PROGRESS;

    public ScaleProgressDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ScaleProgressDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        //forbidden dialog drak background 
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = 0f;
        window.setAttributes(lp);
        //forbidden dialog animatinon 
        window.setWindowAnimations(0);
        //set the dialog for full screen
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mScaleProgressBar = new ScaleProgressBar(mContext);
        //set this dailog for callback
        mScaleProgressBar.setDialog(this);
        MAX_PROGRESS = mScaleProgressBar.MAX_PROGRESS;
        //init scaleprogressdialog size
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mScaleProgressBar.setLayoutParams(params);
        setContentView(mScaleProgressBar);
    }

    public void setProgress(int progress) {
        mScaleProgressBar.setProgress(progress);
    }

    @Override
    public void callDismiss() {
        dismiss();
    }

}
