package com.example.student.memory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {
    private int mSize;
    private int mScore;
    private int mTries;
    private int mShown;
    private boolean mEnded = false;

    private ArrayList<String> mValueOptions;
    private ArrayList<Button> mButtons;

    private Button mLastButton;
    private Button mNewGameButton;
    private Button mEndButton;

    private TextView mScoreView;
    private TextView mTriesView;
    private ViewGroup mRow1;
    private ViewGroup mRow2;
    private ViewGroup mRow3;
    private ViewGroup mRow4;
    private ViewGroup mRow5;
    private ViewGroup mRow6;
    private ViewGroup mRow7;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mRow1 = (ViewGroup) findViewById(R.id.row_one);
        mRow2 = (ViewGroup) findViewById(R.id.row_two);
        mRow3 = (ViewGroup) findViewById(R.id.row_three);
        mRow4 = (ViewGroup) findViewById(R.id.row_four);
        mRow5 = (ViewGroup) findViewById(R.id.row_five);
        mRow6 = (ViewGroup) findViewById(R.id.row_six);
        mRow7 = (ViewGroup) findViewById(R.id.row_seven);
        mSize = getIntent().getIntExtra(Game.EXTRA_SIZE, 4);
        mScoreView = (TextView)findViewById(R.id.score);
        mTriesView = (TextView)findViewById(R.id.tries);

        mButtons = new ArrayList<>();
        mValueOptions = new ArrayList<>();

        mValueOptions.add("Monte");
        mValueOptions.add("Nathan");
        mValueOptions.add("Abby");
        mValueOptions.add("Vincent");
        mValueOptions.add("Steven");
        mValueOptions.add("Selena");
        mValueOptions.add("Lupita");
        mValueOptions.add("Tiffany");
        mValueOptions.add("Tara");
        mValueOptions.add("Brandon");

        createButtons();
    }//end onCreate(Bundle)

    public void createButtons(){
        mShown = 0;
        mScore = 0;
        mTries = (int)Math.floor(0.75 * mSize);
        mLastButton = null;

        for (int i = 0; i < mSize/2; i++){
            final int j = i;
            final Button a = new Button(this);
            final Button b = new Button(this);
            a.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            b.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            a.setText("");
            b.setText("");
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mEnded) {
                        if (mShown < 2) {
                            a.setText(mValueOptions.get(j));
                        }
                        if (!a.equals(mLastButton) || mLastButton == null) {
                            mShown++;
                            if (mShown == 2) {
                                validateButtons(a);
                            }
                            mLastButton = a;
                        }
                    }
                    else {
                        a.setText(mValueOptions.get(j));
                    }
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mEnded) {
                        if (mShown < 2) {
                            b.setText(mValueOptions.get(j));
                        }
                        if (!b.equals(mLastButton) || mLastButton == null) {
                            mShown++;
                            if (mShown == 2) {
                                validateButtons(b);
                            }
                            mLastButton = b;
                        }
                    }
                    else {
                        b.setText(mValueOptions.get(j));
                    }
                }
            });
            mButtons.add(a);
            mButtons.add(b);
        }
        Collections.shuffle(mButtons);

        for (int i = 0; i < mSize; i++) {
            if (i < 3) {
                mRow1.addView(mButtons.get(i));
            } else if (i >= 3 && i < 6) {
                mRow2.addView(mButtons.get(i));
            } else if (i >= 6 && i < 9) {
                mRow3.addView(mButtons.get(i));
            } else if (i >= 9 && i < 12) {
                mRow4.addView(mButtons.get(i));
            } else if (i >= 12 && i < 15) {
                mRow5.addView(mButtons.get(i));
            } else if (i >= 15 && i < 18) {
                mRow6.addView(mButtons.get(i));
            } else {
                mRow7.addView(mButtons.get(i));
            }
        }

        mNewGameButton = (Button)findViewById(R.id.new_game);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnded = false;
                shuffleButtons();
            }
        });
        mEndButton = (Button)findViewById(R.id.show_board);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEnded = true;
                for (Button b : mButtons) {
                    b.callOnClick();
                    b.setEnabled(false);
                }
                Toast.makeText(GameActivity.this, "Game Over", Toast.LENGTH_LONG).show();
            }
        });
        updateText();
    }//end createButtons()


    private void validateButtons(Button button){
        boolean matches = false;
        if (button.getText().equals(mLastButton.getText())) {
            mScore += 100;
            matches = true;
        }
        if (matches) {
            if (mScore == mSize * 50) {
                Toast.makeText(GameActivity.this, "You Win!", Toast.LENGTH_LONG).show();
                button.setEnabled(false);
                mLastButton.setEnabled(false);
            } else {
                Toast.makeText(GameActivity.this, "Match Found!", Toast.LENGTH_SHORT).show();
                button.setEnabled(false);
                mLastButton.setEnabled(false);
            }
        } else {
            mTries--;
            if (mTries <= 0) {
                mEndButton.callOnClick();
            } else {
                Toast.makeText(GameActivity.this, "Not a Match", Toast.LENGTH_SHORT).show();
                button.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetButtons();
                    }
                }, 1000);
            }
        }
        mShown = 0;
        mLastButton = null;
        updateText();
    }//end validateButtons()
    private  void shuffleButtons(){
        resetButtons();
        Collections.shuffle(mValueOptions);
        mLastButton = null;
        updateText();
    }//end shuffleButtons()
    private void updateText() {
        mScoreView.setText("Score: " + mScore);
        mTriesView.setText("Tries Remaining: " + mTries);
    }//end updateText()

    private void resetButtons(){
        if (!mEnded) {
            if (mTries > 0 && mScore < mSize * 50) {
                for (Button b : mButtons) {
                    if (b.isEnabled()) {
                        b.setText("");
                    }
                }
            } else {
                mScore = 0;
                mTries = (int) Math.floor(0.75 * mSize);
                for (Button b : mButtons) {
                    if (!b.isEnabled()) {
                        b.setEnabled(true);
                    }
                    b.setText("");
                }
            }
        }
        else {
            mScore = 0;
            mTries = (int) Math.floor(0.75 * mSize);
            for (Button b: mButtons){
                b.setEnabled(true);
                b.setText("");
            }
        }
    }// End of resetButtons()
}// End of GameActivity class