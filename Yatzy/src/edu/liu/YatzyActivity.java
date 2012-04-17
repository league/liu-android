package edu.liu;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YatzyActivity extends Activity {
	final int NUM_DICE = 5;
	Die[] dice = new Die[NUM_DICE];
	Random rng = new Random();
	Button roll;
	int rollsLeft = 3;
	ThreesScore threes;
	
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

        roll = new Button(this);
        roll.setText("Roll");
        roll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rollDice();
			}
		});
        main.addView(roll);

        threes = new ThreesScore(this);
        main.addView(threes);
        
        Button restart = new Button(this);
        restart.setText("Restart");
        restart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				restart();
			}
		});
        main.addView(restart);
        
        TextView tv = new TextView(this);
        tv.setText("Hello, world.");
        main.addView(tv);
        
        setContentView(main);
    }

	void rollDice() {
		if(rollsLeft > 0) {
			for(int i = 0; i < NUM_DICE; i++) {
				if(!dice[i].on) {
					dice[i].value = rng.nextInt(6);
					dice[i].setResource();
				}
			}
			setRollsLeft(rollsLeft-1);
			threes.computeScore();
		}
	}

	void setRollsLeft(int r) {
		rollsLeft = r;
		roll.setText("Roll (" + r + " left)");
		roll.setEnabled(rollsLeft > 0);
	}
	
	void restart() {
		setRollsLeft(3);
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
    
    class ThreesScore extends LinearLayout {
    	Button b;
    	TextView t;
    	int ourScore;
		public ThreesScore(Context context) {
			super(context);
			b = new Button(context);
			b.setText("Threes");
			addView(b);
			t = new TextView(context);
			t.setText("-");
			addView(t);
		}
		void computeScore() {
			int sum = 0;
			for(int i = 0; i < NUM_DICE; i++) {
				if(dice[i].value == 2) {
					sum += 3;
				}
			}
			setOurScore(sum);
		}
		void setOurScore(int s) {
			ourScore = s;
			t.setText(""+s);
		}
    }
}