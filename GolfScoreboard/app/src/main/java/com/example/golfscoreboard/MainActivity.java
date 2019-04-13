package com.example.golfscoreboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final Scoreboard scoreboard = new Scoreboard();

    Button hole1Plus;
    Button hole1Minus;
    Button hole2Plus;
    Button hole2Minus;

    TextView hole1Score;
    TextView hole2Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hole1Plus = findViewById(R.id.hole1Plus);
        hole1Minus = findViewById(R.id.hole1Minus);
        hole2Plus = findViewById(R.id.hole2Plus);
        hole2Minus = findViewById(R.id.hole2Minus);

        hole1Score = findViewById(R.id.hole1Score);
        hole2Score = findViewById(R.id.hole2Score);

        hole1Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hole1Score.setText(increaseHoleScore(1));
            }
        });
        hole1Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hole1Score.setText(decreaseHoleScore(1));
            }
        });
        hole2Plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hole2Score.setText(increaseHoleScore(2));
            }
        });
        hole2Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hole2Score.setText(decreaseHoleScore(2));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearStroke:
                scoreboard.resetScore();
                resetAllScores();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private String increaseHoleScore(int hole) {
        scoreboard.increaseScore(hole);
        return scoreboard.getHoleScore(hole) + "";
    }
    private String decreaseHoleScore(int hole) {
        scoreboard.decreaseScore(hole);
        return scoreboard.getHoleScore(hole) + "";
    }

    private void resetAllScores() {
        hole1Score.setText("0");
        hole2Score.setText("0");
    }

    //TODO: Remember the scores we set (even if we close the app)

}
