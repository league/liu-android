package edu.liu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YatzyActivity extends Activity {
	final int NUM_DICE = 5;
	Die[] dice = new Die[NUM_DICE];
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);

        LinearLayout diceRow = new LinearLayout(this);
        for(int i = 0; i < NUM_DICE; i++) {
        	dice[i] = new Die(this, i);
        	diceRow.addView(dice[i]);
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
    
    class Die extends ImageView implements View.OnClickListener {
		int value;
    	boolean on;
    	public Die(Context context, int value) {
			super(context);
			setOnClickListener(this);
			this.value = value;
			this.on = false;
			setResource();
		}
    	void setResource() {
    		if(on) {
    			setImageResource(onDice[value]);
    		}
    		else {
    			setImageResource(offDice[value]);
    		}
    	}
		@Override
		public void onClick(View v) {
			this.on = !this.on;
			setResource();
		}
    }
}