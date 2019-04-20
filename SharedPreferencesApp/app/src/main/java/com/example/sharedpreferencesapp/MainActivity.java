package com.example.sharedpreferencesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_FILE = "com.example.sharedpreferencesapp.preferences";    // File where data will be saved
    private static final String KEY_EDITTEXT = "key_edittext";
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String editTextString = sharedPreferences.getString(KEY_EDITTEXT, "");  // Loads the value saved in KEY_EDITTEXT in preferences
        editText.setText(editTextString);
    }

    @Override
    protected void onPause() {
        super.onPause();

        editor.putString(KEY_EDITTEXT, editText.getText().toString());
        editor.apply(); // saves changes to shared preferences value
    }
}
