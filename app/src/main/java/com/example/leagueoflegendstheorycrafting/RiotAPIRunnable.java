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
import java.net.HttpURLConnection;

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
            //Set up HTTP connection based on which activity is currently using this Runnable
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
                final RiotAPIToastManager toaster = new RiotAPIToastManager(activityContext);

                //Get General Summoner Info
                String json_summoner_info_response = riot_api.summonerInfoByName(
                        nameInput.getText().toString());
                final Summoner summoner_info;
                Summoner summoner_null_check = json_manip.fromJson(
                        json_summoner_info_response, Summoner.class);

                if (summoner_null_check != null) {
                    summoner_info = new Summoner(summoner_null_check);
                    summoner_info.setSummonerV4ResponseCode(riot_api.getResponseCode());
                }
                else {
                    summoner_info = new Summoner();
                    summoner_info.setSummonerV4ResponseCode(riot_api.getResponseCode());
                }


                //Making sure the searched summoner exists
                if (summoner_info.getSummonerV4ResponseCode() == HttpURLConnection.HTTP_OK) {
                    //If the searched summoner exists. Do the following.....

                    //Get Live Game Data
                    String json_summoner_live_game_response = riot_api.liveGameBySummonerID(
                            summoner_info.getSummonerID());
                    summoner_info.addLiveGame(json_manip, json_summoner_live_game_response);
                    summoner_info.setSpectatorV4ResponseCode(riot_api.getResponseCode());

                    //Get's the ranked information of the searched summoner's live game participants
                    //if the searched summoner is in a game.
                    if (summoner_info.isInGame()) {
                        riot_api.addLiveGameParticipantRankedInfo(summoner_info.current_match._participants, summoner_info);
                    } else {
                        String json_summoner_ranked_info = riot_api.currentRankBySummonerID(summoner_info.getSummonerID());
                        summoner_info.addRankedInfo(json_manip, json_summoner_ranked_info);
                    }

                    //This if statement is used to determine if this runnable is used in the MainActivity or MatchInfo
                    if (startActivity) {
                        if (!summoner_info.getSummonerName().isEmpty()) {

                            //Start the new Activity (MatchInfo.java)
                            Intent pass_summoner = new Intent(referenceToMain,
                                    MatchInfo.class);
                            pass_summoner.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            pass_summoner.putExtra("Summoner", summoner_info);
                            activityContext.startActivity(pass_summoner);
                        }
                    } else {
                        referenceToMatchInfo.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                referenceToMatchInfo.displaySummonerInfo(summoner_info);
                            }
                        });
                    }
                }
                else {
                    if (referenceToMatchInfo != null) {
                        referenceToMatchInfo.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toaster.makeToast(summoner_info.getSummonerV4ResponseCode(),
                                        RiotAPIToastRequest.SUMMONER_INFO);
                            }
                        });
                    }
                    else if (referenceToMain != null) {
                        referenceToMain.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                toaster.makeToast(summoner_info.getSummonerV4ResponseCode(),
                                        RiotAPIToastRequest.SUMMONER_INFO);
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
