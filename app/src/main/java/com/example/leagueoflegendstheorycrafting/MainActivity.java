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

                Thread getRiotAPI = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Set up HTTP connection
                            RiotAPIConnection riot_api = new RiotAPIConnection();
                            Gson json_manip = new Gson();

                            //Get General Summoner Info
                            String json_summoner_info_response = riot_api.summonerInfoByName(
                                    nameInput.getText().toString());
                            Summoner summoner_info = new Summoner(json_manip.fromJson(
                                    json_summoner_info_response, Summoner.class));
                            summoner_info.setSummonerV4ResponseCode(riot_api.getResponseCode());

                            //Get Ranked info
                            String json_summoner_rank_response = riot_api.currentRankBySummonerID(
                                    summoner_info.getSummonerID());
                            summoner_info.addRankedInfo(json_manip, json_summoner_rank_response);
                            summoner_info.setLeagueV4ResponseCode(riot_api.getResponseCode());

                            //Get Live Game Data
                            String json_summoner_live_game_response = riot_api.liveGameBySummonerID(
                                    summoner_info.getSummonerID());
                            summoner_info.addLiveGame(json_manip, json_summoner_live_game_response);
                            summoner_info.setSpectatorV4ResponseCode(riot_api.getResponseCode());

                            //Start the new Activity (MatchInfo.java)
                            Intent pass_summoner = new Intent(MainActivity.this,
                                    MatchInfo.class );
                            pass_summoner.putExtra("Summoner", summoner_info);
                            startActivity(pass_summoner);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

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
                        this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }


}
