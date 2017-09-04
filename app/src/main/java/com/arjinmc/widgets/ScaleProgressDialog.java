package com.arjinmc.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

/**
 * ScaleProgressDialog
 * @description 
 * @author Eminem Lu
 * @email arjinmc@hotmail.com
 * @date 2015/6/18
 */
public class ScaleProgressDialog extends AlertDialog implements ScaleProgressBar.OnAnimationFinishListener{
	
	private Context mContext;
	private ScaleProgressBar spbProgressBar;
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
                 lp.dimAmount =0f;
        window.setAttributes(lp);    
        //forbidden dialog animatinon 
        window.setWindowAnimations(0);
        //set the dialog for full screen
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        spbProgressBar = new ScaleProgressBar(mContext);
        //set this dailog for callback
        spbProgressBar.setDialog(this);
        MAX_PROGRESS = spbProgressBar.MAX_PROGRESS;
        //init scaleprogressdialog size
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		spbProgressBar.setLayoutParams(params);
		setContentView(spbProgressBar);
	}
	
	public void setProgress(int progress){
		spbProgressBar.setProgress(progress);
	}

	@Override
	public void callDismiss() {
		dismiss();
	}

}
