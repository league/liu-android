package edu.liu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YatzyActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);

        LinearLayout diceRow = new LinearLayout(this);
        for(int d : onDice) {
        	ImageView im = new ImageView(this);
        	im.setImageResource(d);
        	diceRow.addView(im);
        }
        main.addView(diceRow);
        
        TextView tv = new TextView(this);
        tv.setText("Hello, world.");
        main.addView(tv);
        
        setContentView(main);
    }
    
    static int[] onDice = new int[] {
    	R.drawable.die1, R.drawable.die2, R.drawable.die3,
    	R.drawable.die4, R.drawable.die5, R.drawable.die6,
    };
    static int[] offDice = new int[] {
    	R.drawable.die1off, R.drawable.die2off, R.drawable.die3off,
    	R.drawable.die4off, R.drawable.die5off, R.drawable.die6off,
    };
}