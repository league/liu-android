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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			Path path = new Path();
			path.moveTo(event.getX(0), event.getY(0));
			activity.paths.add(new PathColorPair(path, activity.currentColor));
			invalidate();
			return true;
		}
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP: {
			PathColorPair pc = activity.paths.get(activity.paths.size()-1);
			pc.path.lineTo(event.getX(0),event.getY(0));
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
		paint.setStyle(Paint.Style.STROKE);
		for(PathColorPair pc : activity.paths) {
			paint.setColor(pc.color);
			canvas.drawPath(pc.path, paint);
		}
	}

}
