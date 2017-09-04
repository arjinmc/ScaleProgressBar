package com.arjinmc.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * ScaleProgressBar
 *
 * @author Eminem Loo
 * @description
 * @email arjinmc@hotmail.com
 * @date 2015/6/17
 */
public class ScaleProgressBar extends View {

    /**
     * current mProgress
     */
    private int mProgress = 0;
    /**
     * max value of mProgress
     */
    public final int MAX_PROGRESS = 100;

    /**
     * mProgress path color
     */
    private final int COLOR_PROGETSS = 0xffffffff;
    /**
     * the small circle color
     */
    private final int COLOR_S_CIRCLE = 0xffffffff;
    private final int COLOR_TRANSLUCENT = 0xb0000000;
    /**
     * the small circle radius
     */
    private final int RADIUS_PROGRESS = 20;
    /**
     * the big circle radius
     */
    private final int RADIUS_BIG_CIRCLE = 100;

    private final int DURATION_TIME = 2000;
    private final int DURATION_UNIT = 200;
    /**
     * the unit of alter lenth for circle
     */
    private final int ALTER_LENTH = 2;
    /**
     * the path width for mProgress
     */
    private final int PATH_WIDTH = 2;
    /**
     * the path width for text
     */
    private final int TEXT_WIDTH = 1;
    /**
     * the size for text
     */
    private final int TEXT_SIZE = 20;
    /**
     * the text maring top
     */
    private final int MARGIN_TOP = 10;

    /**
     * the progress paint
     */
    private Paint mProgressPaint;
    /**
     * the progress recf
     */
    private RectF mProgressRectF = new RectF();
    /**
     * the small circle paint
     */
    private Paint mSamllCirclePaint;
    /**
     * the square paint
     */
    private Paint mSquarePaint;
    /**
     * the text paint
     */
    private Paint mTextPaint;

    private ScaleProgressDialog mScaleProgressDialog;

    /**
     * a timer for drawing animation
     */
    private CountDownTimer timer = new CountDownTimer(DURATION_TIME, DURATION_UNIT) {

        @Override
        public void onTick(long millisUntilFinished) {
            mProgress++;
            invalidate();
        }

        @Override
        public void onFinish() {
            if (mScaleProgressDialog != null) {
                mScaleProgressDialog.callDismiss();
            } else {
                ScaleProgressBar.this.setVisibility(View.GONE);
            }
        }
    };

    public ScaleProgressBar(Context context) {
        super(context);
        initPaints();
    }

    public ScaleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaints();
    }

    public ScaleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaints();
    }

    public void setDialog(ScaleProgressDialog spDialog) {
        this.mScaleProgressDialog = spDialog;
    }


    private void initPaints() {
        //init Paints and RectF
        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(COLOR_PROGETSS);
        mProgressPaint.setStrokeWidth(PATH_WIDTH);
        mProgressPaint.setAntiAlias(true);

        mSamllCirclePaint = new Paint();
        mSamllCirclePaint.setStyle(Paint.Style.FILL);
        mSamllCirclePaint.setColor(COLOR_S_CIRCLE);
        mSamllCirclePaint.setAntiAlias(true);

        mSquarePaint = new Paint();
        mSquarePaint.setStyle(Paint.Style.FILL);
        mSquarePaint.setColor(COLOR_TRANSLUCENT);
        mSquarePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(COLOR_PROGETSS);
        mTextPaint.setStrokeWidth(TEXT_WIDTH);
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int halfWidth = getWidth() / 2;
        int halfHeight = getHeight() / 2;
        //this way to draw the path for mProgress
        if (mProgress <= MAX_PROGRESS) {
            canvas.drawColor(COLOR_TRANSLUCENT);
            mProgressRectF.top = halfHeight - RADIUS_PROGRESS;
            mProgressRectF.bottom = halfHeight + RADIUS_PROGRESS;
            mProgressRectF.left = halfWidth - RADIUS_PROGRESS;
            mProgressRectF.right = halfWidth + RADIUS_PROGRESS;
            canvas.drawArc(mProgressRectF, -90, ((float) mProgress / (float) MAX_PROGRESS) * 360, false, mProgressPaint);

            //draw a text to show loading percent
            String textString = "Loading" + (int) ((float) mProgress / (float) MAX_PROGRESS * 100) + "%";
            FontMetrics fontMetrics = mProgressPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            float baseY = mProgressRectF.bottom + (bottom - top) + MARGIN_TOP;
            canvas.drawText(textString, halfWidth, baseY, mTextPaint);

            //canvas.save();
            //this way to draw the images when animation start
        } else {
            int alter = mProgress - MAX_PROGRESS;
            Path path = new Path();
            path.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
            path.addCircle(halfWidth, halfHeight, RADIUS_BIG_CIRCLE + alter * ALTER_LENTH, Path.Direction.CCW);
            canvas.drawPath(path, mSquarePaint);
            canvas.drawCircle(halfWidth, halfHeight, RADIUS_PROGRESS - alter * ALTER_LENTH, mSamllCirclePaint);
            //canvas.save();
        }
        super.onDraw(canvas);
    }

    public void setProgress(int progress) {
        if (mScaleProgressDialog == null && getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
        }
        this.mProgress = progress;
        invalidate();
        if (progress == MAX_PROGRESS) {
            timer.start();
        }
    }

    //to callback ScaleProgressDialog to dismiss
    public interface OnAnimationFinishListener {
        public void callDismiss();
    }

}
