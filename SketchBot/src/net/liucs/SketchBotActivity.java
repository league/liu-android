package net.liucs;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.Path;

public class SketchBotActivity extends Activity {

	/* State of app: */
	ArrayList<Path> paths = new ArrayList<Path>();
	SketchBotView view;
	
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
    }
}