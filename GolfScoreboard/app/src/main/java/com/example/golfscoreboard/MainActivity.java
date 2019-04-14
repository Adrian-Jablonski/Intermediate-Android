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
    private static final String[] KEY_SCORE = {"key_score1", "key_score2"};
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    final Scoreboard scoreboard = new Scoreboard();

    Button [] holePlusBtn = new Button[2];
    Button [] holeMinusBtn = new Button[2];
    TextView [] holeScores = new TextView[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        holePlusBtn[0] = findViewById(R.id.hole1Plus);
        holeMinusBtn[0] = findViewById(R.id.hole1Minus);
        holePlusBtn[1] = findViewById(R.id.hole2Plus);
        holeMinusBtn[1] = findViewById(R.id.hole2Minus);

        holeScores[0] = findViewById(R.id.hole1Score);
        holeScores[1] = findViewById(R.id.hole2Score);

        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        loadScores();

        for (int i = 0; i < holePlusBtn.length; i++) {
            final int finalI = i;
            holePlusBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holeScores[finalI].setText(increaseHoleScore(finalI));
                }
            });
            holeMinusBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holeScores[finalI].setText(decreaseHoleScore(finalI));
                }
            });
        }
    }

    private void loadScores() {
        for (int i = 0; i < KEY_SCORE.length; i++) {
            String loadScore = sharedPreferences.getString(KEY_SCORE[i], "");
            holeScores[i].setText(loadScore);
            scoreboard.setHoleScore(i, loadScore);
        }
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

        for (int i = 0; i < KEY_SCORE.length; i++) {
            editor.putString(KEY_SCORE[i], scoreboard.getHoleScore(i));
        }

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
        for (TextView holeScore : holeScores) {
            holeScore.setText("0");
        }
    }

}
