package com.example.leagueoflegendstheorycrafting;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.service.autofill.FieldClassification;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;

import java.io.IOException;
public class RiotAPIRunnable implements Runnable {
    private MainActivity referenceToMain;
    private MatchInfo referenceToMatchInfo;

    private Context activityContext;
    private boolean startActivity;

    private EditText nameInput;

    RiotAPIRunnable(MainActivity main, MatchInfo match, Context context, EditText nameInput, boolean startActivity) {
        this.referenceToMain = main;
        this.referenceToMatchInfo = match;
        this.activityContext = context;
        this.nameInput = nameInput;
        this.startActivity = startActivity;

    }
    @Override
    public void run() {
        try {
            Looper.prepare();
            //Set up HTTP connection
            RiotAPIConnection riot_api = null;
            if (referenceToMain != null) {
                riot_api = new RiotAPIConnection(referenceToMain);
            }
            else if (referenceToMatchInfo != null) {
                riot_api = new RiotAPIConnection(referenceToMatchInfo);
            }
            Gson json_manip = new Gson();

            if (riot_api != null) {
                //Set up Toast
                RiotAPIToastManager toaster = new RiotAPIToastManager(activityContext);

                //Get General Summoner Info
                String json_summoner_info_response = riot_api.summonerInfoByName(
                        nameInput.getText().toString());
                final Summoner summoner_info = new Summoner(json_manip.fromJson(
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

                if (startActivity) {
                    if (!summoner_info.getSummonerName().isEmpty()) {
                        //Start the new Activity (MatchInfo.java)
                        Intent pass_summoner = new Intent(referenceToMain,
                                MatchInfo.class);
                        pass_summoner.putExtra("Summoner", summoner_info);
                        activityContext.startActivity(pass_summoner);
                    }
                    else {

                    }
                } else {
                    if (referenceToMain != null) {
                        referenceToMain.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                referenceToMatchInfo.displayLiveGameInfo(summoner_info);
                            }
                        });
                    }
                    else {
                        referenceToMatchInfo.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                referenceToMatchInfo.displaySummonerInfo(summoner_info);
                            }
                        });
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
