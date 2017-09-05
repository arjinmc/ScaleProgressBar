package com.arjinmc.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
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

    private final int COLOR_TRANSLUCENT = 0xb0000000;

    private final int DEFAULT_COLOR_PROGRESS = 0xffffffff;
    /**
     * progress path color
     */
    private int mProgressColor = DEFAULT_COLOR_PROGRESS;

    private final int DEFAULT_COLOR_SMALL_CIRCLE = 0xffffffff;
    /**
     * the small circle color
     */
    private int mScaleSmallCircleColor = DEFAULT_COLOR_SMALL_CIRCLE;

    private final int RADIUS_SMALL_CIRCLE = 20;
    /**
     * the small circle radius
     */
    private int mScaleSmallCircleRadius = RADIUS_SMALL_CIRCLE;

    private final int RADIUS_BIG_CIRCLE = 100;
    /**
     * the big circle radius
     */
    private int mScaleBigCircleRadius = RADIUS_BIG_CIRCLE;

    private final int DURATION_TIME = 2000;
    private final int DURATION_UNIT = 200;

    private final int DEFAULT_ALTER_LENGTH = 2;
    /**
     * the unit of alter length for circle
     */
    private int mAlterLength = DEFAULT_ALTER_LENGTH;

    private final int DEFAULT_PROGRESS_THICKNESS = 2;
    /**
     * the path width for mProgress
     */
    private int mProgressThickness = DEFAULT_PROGRESS_THICKNESS;

    /**
     * the color of text
     */
    private int mTextColor = DEFAULT_COLOR_PROGRESS;

    private final int DEFAULT_TEXT_SIZE = 20;
    /**
     * the size for text
     */
    private int mTextSize = DEFAULT_TEXT_SIZE;

    private final int DEFAULT_TEXT_MARGIN_TOP = 10;
    /**
     * the text margin top
     */
    private int mTextMarginTop = DEFAULT_TEXT_MARGIN_TOP;

    /**
     * loading text
     */
    private String mLoadingText = "";

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
    private Paint mSmallCirclePaint;
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
                mScaleProgressDialog.dismiss();
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
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStrokeWidth(mProgressThickness);
        mProgressPaint.setAntiAlias(true);

        mSmallCirclePaint = new Paint();
        mSmallCirclePaint.setStyle(Paint.Style.FILL);
        mSmallCirclePaint.setColor(mScaleSmallCircleColor);
        mSmallCirclePaint.setAntiAlias(true);

        mSquarePaint = new Paint();
        mSquarePaint.setStyle(Paint.Style.FILL);
        mSquarePaint.setColor(COLOR_TRANSLUCENT);
        mSquarePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
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
            mProgressRectF.top = halfHeight - mScaleSmallCircleRadius;
            mProgressRectF.bottom = halfHeight + mScaleSmallCircleRadius;
            mProgressRectF.left = halfWidth - mScaleSmallCircleRadius;
            mProgressRectF.right = halfWidth + mScaleSmallCircleRadius;
            canvas.drawArc(mProgressRectF, -90, ((float) mProgress / (float) MAX_PROGRESS) * 360, false, mProgressPaint);

            //draw a text to show loading percent
            String textString = mLoadingText + (int) ((float) mProgress / (float) MAX_PROGRESS * 100) + "%";
            FontMetrics fontMetrics = mProgressPaint.getFontMetrics();
            float top = fontMetrics.top;
            float bottom = fontMetrics.bottom;
            float baseY = mProgressRectF.bottom + (bottom - top) + mTextMarginTop;
            canvas.drawText(textString, halfWidth, baseY, mTextPaint);

            //this way to draw the images when animation start
        } else {
            int alter = mProgress - MAX_PROGRESS;
            Path path = new Path();
            path.addRect(0, 0, getWidth(), getHeight(), Path.Direction.CW);
            path.addCircle(halfWidth, halfHeight, mScaleBigCircleRadius + alter * mAlterLength, Path.Direction.CCW);
            canvas.drawPath(path, mSquarePaint);
            canvas.drawCircle(halfWidth, halfHeight, mScaleSmallCircleRadius - alter * mAlterLength, mSmallCirclePaint);
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

    /**
     * set the progress circle color
     *
     * @param color color value
     */
    public void setProgressColor(@ColorInt int color) {
        mProgressColor = color;
        mProgressPaint.setColor(mProgressColor);
    }

    /**
     * set the thickness of progress circle
     *
     * @param thickness the thickness of the circle
     */
    public void setProgressThickness(int thickness) {
        if (thickness <= 0) thickness = DEFAULT_PROGRESS_THICKNESS;
        mProgressThickness = thickness;
        mProgressPaint.setStrokeWidth(mProgressThickness);
    }

    /**
     * set loading text size
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
    }

    /**
     * set the loading text color
     *
     * @param textColor
     */
    public void setTextColor(@ColorInt int textColor) {
        mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
    }

    /**
     * set the marginTop of the loading text below the progress circle
     *
     * @param marginTop
     */
    public void setTextMarginTop(int marginTop) {
        mTextMarginTop = marginTop;
    }

    /**
     * set loading text
     *
     * @param str string to show loading percent
     */
    public void setText(String str) {
        mLoadingText = str;
    }

    /**
     * set scale small circle color
     *
     * @param color
     */
    public void setScaleSmallCircleColor(@ColorInt int color) {
        mScaleSmallCircleColor = color;
    }

    /**
     * set the radius of scale effect small circle
     *
     * @param radius
     */
    public void setScaleSmallCircleRadius(int radius) {
        mScaleSmallCircleRadius = radius;
    }

    /**
     * set the radius of scale effect big circle
     *
     * @param radius
     */
    public void setScaleBigCircleRadius(int radius) {
        mScaleBigCircleRadius = radius;
    }

    /**
     * set the unit for alter scale circle radius
     *
     * @param length
     */
    public void setScaleAlertCircleLength(int length) {
        mAlterLength = length;
    }

    //to callback ScaleProgressDialog to dismiss
    public interface OnAnimationFinishListener {
        public void dismiss();
    }

}
