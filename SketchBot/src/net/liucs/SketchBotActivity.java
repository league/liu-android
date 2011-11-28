package net.liucs;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.Path;

class PathColorPair {
	Path path;
	int color;
	PathColorPair(Path p, int c) {
		path = p;
		color = c;
	}
}

public class SketchBotActivity extends Activity {

	/* State of app: */
	ArrayList<PathColorPair> paths = new ArrayList<PathColorPair>();
	int currentColor = Color.WHITE;
	SketchBotView view;

	class ColorChanger implements View.OnClickListener {
		private int targetColor;
		ColorChanger(int c) {
			targetColor = c;
		}
		@Override public void onClick(View v) {
			currentColor = targetColor;
		}
	}
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        view = (SketchBotView) findViewById(R.id.sketchBotView1);
        view.setParentActivity(this);
        Button clear = (Button) findViewById(R.id.clearButton);
        clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				paths.clear();
				view.invalidate();
			}
        });
        Button redButton = (Button) findViewById(R.id.redButton);
        Button blueButton = (Button) findViewById(R.id.blueButton);
        Button whiteButton = (Button) findViewById(R.id.whiteButton);
        redButton.setOnClickListener(new ColorChanger(Color.RED));
        blueButton.setOnClickListener(new ColorChanger(Color.BLUE));
        whiteButton.setOnClickListener(new ColorChanger(Color.WHITE));
    }
}