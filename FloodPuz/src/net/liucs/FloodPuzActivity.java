package net.liucs;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

class FloodCell extends ImageView {

	private static Random rand = new Random();
	
	public static int images[] = {
		R.drawable.circ_red_32,
		R.drawable.circ_orange_32,
		R.drawable.circ_butter_32,
		R.drawable.circ_cham_32,
		R.drawable.circ_blue_32,
		R.drawable.circ_plum_32
	};
	private int color;
	
	public FloodCell(Context context) {
		super(context);
		setRandomColor();
	}

	public int getColor() {
		return color;
	}
	
	public void setColor(int i) {
		assert(i >= 0 && i < images.length);
		color = i;
		setImageResource(images[i]);
		// invalidate?
	}
	
	public void setRandomColor() {
		setColor(rand.nextInt(images.length));
	}
	
}

public class FloodPuzActivity extends Activity {
	
    final int SIZE = 10;
    final int MAX_MOVES = 21;

    FloodCell[][] board = new FloodCell[SIZE][SIZE];

    int moveCount;    
    TextView movesLeft, winText;
    boolean winner;
    
	int buttonImages[] = {
		R.drawable.circ_red_48,
		R.drawable.circ_orange_48,
		R.drawable.circ_butter_48,
		R.drawable.circ_cham_48,
		R.drawable.circ_blue_48,
		R.drawable.circ_plum_48	
	};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout buttonsRow = new LinearLayout(this);
        for(int i = 0; i < buttonImages.length; i++) {
        	ImageView im = new ImageView(this);
        	im.setImageResource(buttonImages[i]);
        	im.setOnClickListener(new FloodListener(i));
        	buttonsRow.addView(im);
        }
        
        LinearLayout statusRow = new LinearLayout(this);
        Button newButton = new Button(this);
        newButton.setText("New");
        newButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				resetBoard();
			}
		});
        statusRow.addView(newButton);
        movesLeft = new TextView(this);
        setMoveCount(MAX_MOVES);
        statusRow.addView(movesLeft);
        winText = new TextView(this);
        statusRow.addView(winText);
        
        TableLayout table = new TableLayout(this);
        for(int i = 0; i < SIZE; i++) {
        	TableRow row = new TableRow(this);
        	for(int j = 0; j < SIZE; j++) {
        		board[i][j] = new FloodCell(this);
        		row.addView(board[i][j]);
        	}
        	table.addView(row);
        }

        main.addView(table);
        main.addView(statusRow);
        main.addView(buttonsRow);
        setContentView(main);
    }

    private void setMoveCount(int n) {
    	moveCount = n;
    	if(moveCount > 0) {
    		movesLeft.setText("Moves left: " + moveCount);
    	}
    	else {
    		movesLeft.setText("Game over!");
    	}
    }
    
    public void resetBoard() {
    	winner = false;
    	winText.setText("");
    	setMoveCount(MAX_MOVES);
    	for(int i = 0; i < SIZE; i++) {
    		for(int j = 0; j < SIZE; j++) {
    			board[i][j].setRandomColor();
    		}
    	}
    }
    private boolean inBounds(int i, int j) {
    	return i >= 0 && i < SIZE && j >= 0 && j < SIZE;
    }
    
    private boolean okayToVisit(boolean[][] visited, int i, int j, int color) {    	
        return inBounds(i,j) && !visited[i][j] && board[i][j].getColor() == color;
    }
    
    private void flood(boolean[][] visited, int i, int j, int prevColor, int newColor) {
        assert(inBounds(i,j));
        assert(board[i][j].getColor() == prevColor);
        visited[i][j] = true;
        board[i][j].setColor(newColor);
        if(okayToVisit(visited, i+1, j, prevColor)) { // down
            flood(visited, i+1, j, prevColor, newColor);
        }
        if(okayToVisit(visited, i-1, j, prevColor)) { // up
            flood(visited, i-1, j, prevColor, newColor);
        }
        if(okayToVisit(visited, i, j+1, prevColor)) { // right
            flood(visited, i, j+1, prevColor, newColor);
        }
        if(okayToVisit(visited, i, j-1, prevColor)) { // left
            flood(visited, i, j-1, prevColor, newColor);
        }    	
    }
    
    private void flood(int newColor) {
    	flood(new boolean[SIZE][SIZE], 0, 0, board[0][0].getColor(), newColor);
    }    
    
    public void detectWin() {
    	for(int i = 0; i < SIZE; i++) {
    		for(int j = 0; j < SIZE; j++) {
    			if(board[i][j].getColor() != board[0][0].getColor()) {
    				return;
    			}
    		}
    	}
    	winner = true;
    	winText.setText("You won!");
    }
    
    class FloodListener implements View.OnClickListener {
    	private int id;
    	public FloodListener(int id) {
    		assert(id >= 0 && id < buttonImages.length);
    		this.id = id;
    	}
		public void onClick(View v) {
			if(!winner && moveCount > 0 && board[0][0].getColor() != id) { // ignore repeated moves
				setMoveCount(moveCount-1);
				flood(id);
				detectWin();
			}
		}
    }
}
