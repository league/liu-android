package net.liucs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SketchBotView extends View {

	private SketchBotActivity activity;
	
	public SketchBotView(Context context) {
		super(context);
	}
	
	public SketchBotView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setParentActivity(SketchBotActivity a) {
		this.activity = a;
	}
	
	private int myColor = Color.WHITE;
	
//	private void addMotionPoints(MotionEvent event) {
//		for(int i = 0; i < event.getPointerCount(); i++) {
//			int x = (int) event.getX(i);
//			int y = (int) event.getY(i);
//			points.add(new Point(x, y));
//		}		
//	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Path path = new Path();
			path.moveTo(event.getX(0), event.getY(0));
			activity.paths.add(path);
			invalidate();
			return true;
		}
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP: {
			Path path = activity.paths.get(activity.paths.size()-1);
			path.lineTo(event.getX(0),event.getY(0));
			invalidate();
			return true;
		}
		default:
			return false; // not handled
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(myColor);
		paint.setStyle(Paint.Style.STROKE);
		for(Path p : activity.paths) {
			canvas.drawPath(p, paint);
		}
	}

}
