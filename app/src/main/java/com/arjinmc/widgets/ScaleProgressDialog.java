package com.arjinmc.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import static android.R.attr.paddingTop;
import static android.R.attr.panelColorBackground;
import static android.R.attr.textColor;

/**
 * ScaleProgressDialog
 *
 * @author Eminem Lo
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
        if (mScaleProgressBar == null) {
            mScaleProgressBar = new ScaleProgressBar(mContext);
        }
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

    /**
     * A Builder to create ScaleProgressDialog
     */
    public static class Builder {

        private Context context;
        private Param param;

        public Builder(Context context) {
            this.context = context;
            param = new Param();
        }

        public Builder textColor(@ColorInt int color) {
            param.textColor = color;
            return this;
        }

        public Builder textSize(int textSize) {
            param.textSize = textSize;
            return this;
        }

        public Builder textMarginTop(int margin) {
            param.textMarginTop = margin;
            return this;
        }

        public Builder text(String text) {
            param.text = text;
            return this;
        }

        public Builder progressColor(@ColorInt int color) {
            param.progressColor = color;
            return this;
        }

        public Builder progressThickness(int thickness) {
            param.progressThickness = thickness;
            return this;
        }

        public Builder scaleSmallCircleColor(@ColorInt int color) {
            param.scaleSmallCircleColor = color;
            return this;
        }

        public Builder scaleSmallCircleRadius(int radius) {
            param.scaleSmallCircleRadius = radius;
            return this;
        }

        public Builder scaleBigCircleRadius(int radius) {
            param.scaleBigCircleRadius = radius;
            return this;
        }

        public Builder alterLength(int alterLength) {
            param.alterLength = alterLength;
            return this;
        }

        public ScaleProgressDialog create() {

            ScaleProgressDialog scaleProgressDialog = new ScaleProgressDialog(context);
            scaleProgressDialog.mScaleProgressBar = new ScaleProgressBar(context);
            if (param.textColor != 0)
                scaleProgressDialog.mScaleProgressBar.setTextColor(param.textColor);
            if (param.textSize != 0)
                scaleProgressDialog.mScaleProgressBar.setTextSize(param.textSize);
            if (param.textMarginTop != 0)
                scaleProgressDialog.mScaleProgressBar.setTextMarginTop(param.textMarginTop);
            if (!TextUtils.isEmpty(param.text))
                scaleProgressDialog.mScaleProgressBar.setText(param.text);
            if (param.progressColor != 0)
                scaleProgressDialog.mScaleProgressBar.setProgressColor(param.progressColor);
            if (param.progressThickness != 0)
                scaleProgressDialog.mScaleProgressBar.setProgressThickness(param.progressThickness);
            if (param.scaleSmallCircleColor != 0)
                scaleProgressDialog.mScaleProgressBar.setScaleSmallCircleColor(param.scaleSmallCircleColor);
            if (param.scaleSmallCircleRadius != 0)
                scaleProgressDialog.mScaleProgressBar.setScaleSmallCircleRadius(param.scaleSmallCircleRadius);
            if (param.scaleBigCircleRadius != 0)
                scaleProgressDialog.mScaleProgressBar.setScaleBigCircleRadius(param.scaleBigCircleRadius);
            if (param.alterLength != 0)
                scaleProgressDialog.mScaleProgressBar.setScaleAlertCircleLength(param.alterLength);

            return scaleProgressDialog;
        }
    }


    /**
     * params of ScaleProgressDialog
     */
    public static class Param {

        public int textColor;
        public int textSize;
        public int textMarginTop;
        public String text;
        public int progressColor;
        public int progressThickness;
        public int scaleSmallCircleColor;
        public int scaleSmallCircleRadius;
        public int scaleBigCircleRadius;
        public int alterLength;
    }

}
