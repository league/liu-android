package net.liucs;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SketchBotView extends View {

	public SketchBotView(Context context) {
		super(context);
	}
	
	public SketchBotView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	private int myColor = Color.WHITE;
	
	private ArrayList<Point> points = new ArrayList<Point>();

	private void addMotionPoints(MotionEvent event) {
		for(int i = 0; i < event.getPointerCount(); i++) {
			int x = (int) event.getX(i);
			int y = (int) event.getY(i);
			points.add(new Point(x, y));
		}		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			addMotionPoints(event);
			invalidate();
			return true;
//			myColor = (myColor == Color.RED)? Color.YELLOW : Color.RED;
		default:
			return false; // not handled
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint paint = new Paint();
		paint.setColor(myColor);
		Point prev = null;
		for(Point p : points) {
			if(prev != null) {
				canvas.drawLine(prev.x, prev.y, p.x, p.y, paint);
//				canvas.drawPoint(p.x, p.y, paint);
			}
			prev = p;
		}
//		float h = canvas.getHeight();
//		float w = canvas.getWidth();
//		float x = 25;
//		canvas.drawRect(x, x, w-x, h-x, p);
	}

}
