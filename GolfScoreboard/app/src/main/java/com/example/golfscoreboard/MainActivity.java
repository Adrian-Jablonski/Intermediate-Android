package com.example.golfscoreboard;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ListActivity {    //Changed to ListActivity to get listview option
    private static final String PREFS_FILE = "com.example.golfscoreboard.preferences";
    private static final String KEY_STROKECOUNT = "key_strokecount";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Hole[] holes = new Hole[18];
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // Initialize holes
        int strokes = 0;
        for (int i = 0; i < holes.length; i++) {
            strokes = sharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            holes[i] = new Hole("Hole " + (i + 1) + " :", strokes);
        }

        listAdapter = new ListAdapter(this, holes);
        setListAdapter(listAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        for (int i = 0; i < holes.length; i++) {
            editor.putInt(KEY_STROKECOUNT + i, holes[i].getStrokeCount());    // Will create 18 different keys for shared preferences and store 18 values
        }
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear_strokes) {
            editor.clear();     // Clears preferences
            editor.apply();

            for (Hole hole: holes) {
                hole.setStrokeCount(0);
            }
            listAdapter.notifyDataSetChanged();     // Updates view

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
