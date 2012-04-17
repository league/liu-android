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
	ScoreRule[] rules = new ScoreRule[12];
	TextView scoreText;
	int score;
	
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

        scoreText = new TextView(this);
        scoreText.setText("Score: 0");
        main.addView(scoreText);

        rules[0] = new SingleValueRule(this, 3);
        rules[1] = new SingleValueRule(this, 4);
        for(ScoreRule r : rules) {
        	if(r != null) {
        		main.addView(r);
        	}
        }
        
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
	        for(ScoreRule r : rules) {
	        	if(r != null) {
	        		r.computeScore();
	        	}
	        }
		}
	}

	void setScore(int s) {
		score = s;
		scoreText.setText("Score: "+s);
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
    
    abstract class ScoreRule extends LinearLayout {
    	Button b;
    	TextView t;
    	int ourScore;
    	boolean enabled = true;
		public ScoreRule(Context context, String label) {
			super(context);
			b = new Button(context);
			b.setText(label);
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					setScore(score + ourScore);
					enabled = false;
					b.setEnabled(false);
				}
			});
			addView(b);
			t = new TextView(context);
			t.setText("-");
			addView(t);
		}
		abstract void computeScore();
		void setOurScore(int s) {
			ourScore = s;
			t.setText(""+s);
		}
    }

    class SingleValueRule extends ScoreRule {
    	int value;
    	public SingleValueRule(Context context, int value) {
    		super(context, ""+(value+1)+"s");
    		this.value = value;
    	}
    	void computeScore() {
    		if(!enabled) return;
			int sum = 0;
			for(int i = 0; i < NUM_DICE; i++) {
				if(dice[i].value == value) {
					sum += value+1;
				}
			}
			setOurScore(sum);
    	}
    }
}