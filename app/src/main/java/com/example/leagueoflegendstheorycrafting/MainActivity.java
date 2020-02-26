package com.example.leagueoflegendstheorycrafting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    private EditText nameInput;
    private Button button;

    Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeApp();
    }

    private void InitializeApp() {
        button = findViewById(R.id.submit_name);
        nameInput = findViewById(R.id.name_input);
        mainHandler = new Handler(getMainLooper());

        nameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear text when clicked
                nameInput.setText("");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread getRiotAPI = new Thread(new RiotAPIRunnable(MainActivity.this,
                        null, getApplicationContext(), nameInput, true));

                //Begin Riot API calls on new Thread
                getRiotAPI.start();
            }
        });

        nameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    button.performClick();
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(
                        INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }


}
