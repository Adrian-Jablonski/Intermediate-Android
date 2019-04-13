package com.example.golfscoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "com.example.golfscoreboard.preferences";
    private static final String KEY_SCORE1 = "key_score1";
    private static final String KEY_SCORE2 = "key_score2";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
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

        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadScores();


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

    private void loadScores() {
        String loadScore1 = sharedPreferences.getString(KEY_SCORE1, "");
        String loadScore2 = sharedPreferences.getString(KEY_SCORE2, "");
        hole1Score.setText(loadScore1);
        hole2Score.setText(loadScore2);

        scoreboard.setHoleScore(1, loadScore1);
        scoreboard.setHoleScore(2, loadScore2);

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

    @Override
    protected void onPause() {
        super.onPause();

        editor.putString(KEY_SCORE1, scoreboard.getHoleScore(1));
        editor.putString(KEY_SCORE2, scoreboard.getHoleScore(2));

        editor.apply();
    }

    private String increaseHoleScore(int hole) {
        scoreboard.increaseScore(hole);
        return scoreboard.getHoleScore(hole);
    }
    private String decreaseHoleScore(int hole) {
        scoreboard.decreaseScore(hole);
        return scoreboard.getHoleScore(hole);
    }

    private void resetAllScores() {
        hole1Score.setText("0");
        hole2Score.setText("0");
    }

}
