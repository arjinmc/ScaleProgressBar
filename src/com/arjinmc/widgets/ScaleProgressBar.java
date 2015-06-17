package com.arjinmc.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

/**
 * ScaleProgressBar
 * @description 
 * @author Eminem Lu
 * @email arjinmc@hotmail.com
 * @date 2015/6/17
 */
public class ScaleProgressBar extends View{
	
	/**current progress*/
	private int progress = 0;
	/**max value of progress */
	public final int MAX_PROGRESS = 100;
	
	/**progress path color*/
	private final int COLOR_PROGETSS = 0xffffffff;
	/**the small circle color*/
	private final int COLOR_S_CIRCLE = 0xffffffff;
	private final int COLOR_TRANSLUCENT = 0xb0000000;
	/**the small circle radius*/
	private final int RADIUS_PROGRESS = 20;
	/**the big circle radius*/
	private final int RADIUS_BIG_CIRCLE = 100;
	
	private final int DURATION_TIME = 2000;
	private final int DURATION_UNIT = 100;
	/**the unit for alter lenth for circle*/
	private final int ALTER_LENTH = 2;
	
	/**the progress paint*/
	private Paint pPaint;
	/**the progress recf*/
	private RectF pRectF = new RectF();
	/**the small circle paint*/
	private Paint sCirclePaint;
	/**the square paint*/
	private Paint squarePaint;
	
	/**a timer for drawing animation*/
	private CountDownTimer timer = new CountDownTimer(DURATION_TIME,DURATION_UNIT) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			progress++;
			invalidate();
		}
		
		@Override
		public void onFinish() {
			ScaleProgressBar.this.setVisibility(View.GONE);
		}
	};

	public ScaleProgressBar(Context context) {
		super(context);
	}
		
	
	public ScaleProgressBar(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public ScaleProgressBar(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		//init Paints and RectF
		pPaint = new Paint();
		pPaint.setStyle(Paint.Style.STROKE);
		pPaint.setColor(COLOR_PROGETSS);
		
		sCirclePaint = new Paint();
		sCirclePaint.setStyle(Paint.Style.FILL);
		sCirclePaint.setColor(COLOR_S_CIRCLE);
		
		squarePaint = new Paint();
		squarePaint.setStyle(Paint.Style.FILL);
		squarePaint.setColor(COLOR_TRANSLUCENT);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int halfWidth = getWidth() / 2;
		int halfHeight = getHeight() /2;
		if(progress<MAX_PROGRESS){
			canvas.drawColor(COLOR_TRANSLUCENT);
			pRectF.top = halfHeight - RADIUS_PROGRESS;
			pRectF.bottom = halfHeight + RADIUS_PROGRESS;
			pRectF.left = halfWidth - RADIUS_PROGRESS;
			pRectF.right = halfWidth + RADIUS_PROGRESS;
			canvas.drawArc(pRectF, -90, ((float)progress/(float)MAX_PROGRESS)*360, false, pPaint);
			canvas.save();
		}else{
			int alter = progress-MAX_PROGRESS;
			Path path = new Path();
			path.addRect(0, 0,getWidth(),getHeight(),Path.Direction.CW);
			path.addCircle(halfWidth, halfHeight,RADIUS_BIG_CIRCLE+alter*ALTER_LENTH,Path.Direction.CCW);
			canvas.drawPath(path, squarePaint);
			canvas.drawCircle(halfWidth, halfHeight, RADIUS_PROGRESS-alter*ALTER_LENTH, sCirclePaint);
			canvas.save();
		}
		super.onDraw(canvas);
	}
	
	public void setProgress(int progress){
		this.progress = progress;
		invalidate();
		if(progress==MAX_PROGRESS){
			showAnimation();
		}
	}
	
	public void showAnimation(){
		timer.start();
	}
	
	

}
