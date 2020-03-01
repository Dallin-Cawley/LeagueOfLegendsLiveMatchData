package com.example.leagueoflegendstheorycrafting;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MatchInfo extends AppCompatActivity {

    TextView displaySummonerName;
    TextView displaySummonerLvl;
    TextView displaySummonerRank;

    EditText searchAnotherSummoner;

    ImageView summonerIcon;

    Intent receivedSummoner;
    Context matchInfoContext;

    //Team 1 Summoner Name TextViews
    TextView team1TopLaneSummonerName;
    TextView team1MidLaneSummonerName;
    TextView team1JungleSummonerName;
    TextView team1ADCSummonerName;
    TextView team1SupportSummonerName;

    //Team 1 Rank TextViews
    TextView team1TopLaneSummonerRank;
    TextView team1MidLaneSummonerRank;
    TextView team1JungleSummonerRank;
    TextView team1ADCSummonerRank;
    TextView team1SupportSummonerRank;

    //Team 2 Summoner Name TextViews
    TextView team2TopLaneSummonerName;
    TextView team2MidLaneSummonerName;
    TextView team2JungleSummonerName;
    TextView team2ADCSummonerName;
    TextView team2SupportSummonerName;

    //Team 2 Rank TextViews
    TextView team2TopLaneSummonerRank;
    TextView team2MidLaneSummonerRank;
    TextView team2JungleSummonerRank;
    TextView team2ADCSummonerRank;
    TextView team2SupportSummonerRank;


    //Team 1 Champion Picture ImageView
    ImageView team1TopLaneChamp;
    ImageView team1MidLaneChamp;
    ImageView team1JungleChamp;
    ImageView team1ADCCHamp;
    ImageView team1SupportChamp;

    //Team 2 Champion Picture ImageView
    ImageView team2TopLaneChamp;
    ImageView team2MidLaneChamp;
    ImageView team2JungleChamp;
    ImageView team2ADCChamp;
    ImageView team2SupportChamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_info);
        receivedSummoner = getIntent();

        InitializeActivity();
    }

    public void InitializeActivity() {
        matchInfoContext = getApplicationContext();
        final MatchInfo matchInfo = this;

        //Initialize Searched Summoner Display Items
        displaySummonerName = findViewById(R.id.summoner_name_display);
        summonerIcon = findViewById(R.id.summoner_icon);
        displaySummonerLvl = findViewById(R.id.summoner_lvl_display);
        displaySummonerRank = findViewById(R.id.summoner_rank_display);
        searchAnotherSummoner = findViewById(R.id.search_another_summoner);

        //Initialize team champion image views
        team1TopLaneChamp = findViewById(R.id.team1_top_lane_champ_pic);
        team1MidLaneChamp = findViewById(R.id.team1_mid_lane_champ_pic);
        team1JungleChamp = findViewById(R.id.team1_jungle_champ_pic);
        team1ADCCHamp = findViewById(R.id.team1_adc_champ_pic);
        team1SupportChamp = findViewById(R.id.team1_support_champ_pic);

        team2TopLaneChamp = findViewById(R.id.team2_top_lane_champ_pic);
        team2MidLaneChamp = findViewById(R.id.team2_mid_lane_champ_pic);
        team2JungleChamp = findViewById(R.id.team2_jungle_champ_pic);
        team2ADCChamp = findViewById(R.id.team2_adc_champ_pic);
        team2SupportChamp = findViewById(R.id.team2_support_champ_pic);

        //Initialize Searched Summoner Team Information
        team1TopLaneSummonerName = findViewById(R.id.team1_top_summoner_name);
        team1TopLaneSummonerRank = findViewById(R.id.team1_top_rank);
        team1MidLaneSummonerName = findViewById(R.id.team1_mid_summoner_name);
        team1MidLaneSummonerRank = findViewById(R.id.team1_mid_rank);
        team1JungleSummonerName = findViewById(R.id.team1_jungle_summoner_name);
        team1JungleSummonerRank = findViewById(R.id.team1_jungle_rank);
        team1ADCSummonerName = findViewById(R.id.team1_adc_summoner_name);
        team1ADCSummonerRank = findViewById(R.id.team1_adc_rank);
        team1SupportSummonerName = findViewById(R.id.team1_support_summoner_name);
        team1SupportSummonerRank = findViewById(R.id.team1_support_rank);

        team2TopLaneSummonerName = findViewById(R.id.team2_top_summoner_name);
        team2TopLaneSummonerRank = findViewById(R.id.team2_top_rank);
        team2MidLaneSummonerName = findViewById(R.id.team2_mid_summoner_name);
        team2MidLaneSummonerRank = findViewById(R.id.team2_mid_rank);
        team2JungleSummonerName = findViewById(R.id.team2_jungle_summoner_name);
        team2JungleSummonerRank = findViewById(R.id.team2_jungle_rank);
        team2ADCSummonerName = findViewById(R.id.team2_adc_summoner_name);
        team2ADCSummonerRank = findViewById(R.id.team2_adc_rank);
        team2SupportSummonerName = findViewById(R.id.team2_support_summoner_name);
        team2SupportSummonerRank = findViewById(R.id.team2_support_rank);


        //Set up 'done' key event such that another summoner can be searched using the EditText
        //view 'searchAnotherSummoner'
        searchAnotherSummoner.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    new Thread(new RiotAPIRunnable(null, matchInfo,
                            matchInfoContext, searchAnotherSummoner, false)).start();
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });

        //Get the result from our Riot API request for summoner data from the previous activity
        Summoner summoner = (Summoner) receivedSummoner.getSerializableExtra("Summoner");

        displaySummonerInfo(summoner);

    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(), 0);
    }


    /* Making sure that the various views are available and then updating their info with the
       respective 'Summoner' object values */
    public void displaySummonerInfo(Summoner summoner){
        displaySummonerName.setText("");
        displaySummonerRank.setText("");
        displaySummonerLvl.setText("");

        if (summoner != null) {

            //Display toasts based on response codes
            List<Integer> responseCodes = new ArrayList<>();
            responseCodes.add(summoner.getSummonerV4ResponseCode());
            responseCodes.add(summoner.getLeagueV4ResponseCode());
            responseCodes.add(summoner.getSpectatorV4ResponseCode());
            checkResponseCode(responseCodes);

            //displaySummonerName TextView
            if (displaySummonerName != null) {
                displaySummonerName.append(summoner.getSummonerName());
            }

            //displaySummonerLvl TextView
            if (displaySummonerLvl != null) {
                String summonerLevel = "Level: " + summoner.getSummonerLvL();
                displaySummonerLvl.append(summonerLevel);

            }

            //displaySummonerRank TextView
            if (displaySummonerRank != null && !summoner.queue_ranks.isEmpty()) {
                StringBuilder summonerRank = new StringBuilder();

                //Create the proper string for display
                for (RankedInfo temp : summoner.queue_ranks) {
                    summonerRank.append(temp._queue_type + ": " + temp._tier + " " + temp._rank + "\n");
                }

                displaySummonerRank.setText(summonerRank.toString());
            }

            //summonerIcon ImageView
            if (summonerIcon != null) {
                summonerIcon.setBackgroundResource(getProfileIDFilePath(summoner));
            }

            if (summoner.isInGame()) {
                displayLiveGameInfo(summoner);
            }
        }
        else {
            displaySummonerLvl.setText(R.string.summoner_lvl_not_found);
            if (displaySummonerName != null) {
                displaySummonerName.setText(R.string.summoner_name_not_found);
            }
            if (displaySummonerRank != null) {
                displaySummonerRank.setText(R.string.summoner_rank_not_found);
            }

        }

    }

    public void displayLiveGameInfo(Summoner summoner) {

        if (summoner == null || !summoner.isInGame()) {
            System.out.println("Summoner has no live game data");
        }
        else {

            //Team 1 Summoner Name TextViews
            if (team1TopLaneSummonerName != null) {
                team1TopLaneSummonerName.setText(summoner.current_match._participants.get(4)._summoner_name);
            }
            if (team1MidLaneSummonerName != null) {
                team1MidLaneSummonerName.setText(summoner.current_match._participants.get(3)._summoner_name);
            }
            if (team1JungleSummonerName != null) {
                team1JungleSummonerName.setText(summoner.current_match._participants.get(2)._summoner_name);
            }
            if (team1ADCSummonerName != null) {
                team1ADCSummonerName.setText(summoner.current_match._participants.get(1)._summoner_name);
            }
            if (team1SupportSummonerName != null) {
                team1SupportSummonerName.setText(summoner.current_match._participants.get(0)._summoner_name);
            }


            //Team 1 Summoner Rank TextViews
            if (team1TopLaneSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(4);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team1TopLaneSummonerRank.setText(rank_string.toString());
                }

            }
            if (team1MidLaneSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(3);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team1MidLaneSummonerRank.setText(rank_string.toString());
                }
            }
            if (team1JungleSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(2);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team1JungleSummonerRank.setText(rank_string.toString());
                }
            }
            if (team1ADCSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(1);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team1ADCSummonerRank.setText(rank_string.toString());
                }
            }
            if (team1SupportSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(0);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team1SupportSummonerRank.setText(rank_string.toString());
                }
            }

            //Team 2 Summoner Rank TextViews
            if (team2TopLaneSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(9);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team2TopLaneSummonerRank.setText(rank_string.toString());
                }
            }
            if (team2MidLaneSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(8);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team2MidLaneSummonerRank.setText(rank_string.toString());
                }
            }
            if (team2JungleSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(7);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team2JungleSummonerRank.setText(rank_string.toString());
                }
            }
            if (team2ADCSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(6);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team2ADCSummonerRank.setText(rank_string.toString());
                }
            }
            if (team2SupportSummonerRank != null) {
                StringBuilder rank_string = new StringBuilder();

                LiveGameParticipant participant = summoner.current_match._participants.get(5);

                for (int i = 0; i < participant.queue_ranks.size(); i++) {
                    if (participant.queue_ranks.get(i)._queue_type.contains("SOLO")) {
                        rank_string.append(participant.queue_ranks.get(i)._rank + ": " +
                                participant.queue_ranks.get(i)._tier);
                    }

                    team2SupportSummonerRank.setText(rank_string.toString());
                }
            }

            //Create the hashMap that has the champion ID's.
            StringBuilder champion_json = new StringBuilder();
            try {
                Scanner read_file = new Scanner(getAssets().open("champion.json"));
                while (read_file.hasNext()) {
                    champion_json.append(read_file.nextLine());
                }
            }
            catch(IOException i) {
                System.out.println(i);
            }

            ChampionIDContainer champions = new Gson().fromJson(champion_json.toString(), ChampionIDContainer.class);

            //Team 1 Champion ImageViews
            if (team1TopLaneChamp != null) {
                team1TopLaneChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(4), champions));
            }
            if (team1MidLaneChamp != null) {
                team1MidLaneChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(3), champions));
            }
            if (team1JungleChamp != null) {
                team1JungleChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(2), champions));
            }
            if (team1ADCCHamp != null) {
                team1ADCCHamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(1), champions));
            }
            if (team1SupportChamp != null) {
                team1SupportChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(0), champions));
            }

            //Team 2 Summoner Name TextViews
            if (team2TopLaneSummonerName != null) {
                team2TopLaneSummonerName.setText(summoner.current_match._participants.get(9)._summoner_name);
            }
            if (team2MidLaneSummonerName != null) {
                team2MidLaneSummonerName.setText(summoner.current_match._participants.get(8)._summoner_name);
            }
            if (team2JungleSummonerName != null) {
                team2JungleSummonerName.setText(summoner.current_match._participants.get(7)._summoner_name);
            }
            if (team2ADCSummonerName != null) {
                team2ADCSummonerName.setText(summoner.current_match._participants.get(6)._summoner_name);
            }
            if (team2SupportSummonerName != null) {
                team2SupportSummonerName.setText(summoner.current_match._participants.get(5)._summoner_name);
            }

            //Team 2 Champion ImageViews
            if (team2TopLaneChamp != null) {
                team2TopLaneChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(9), champions));
            }
            if (team2MidLaneChamp != null) {
                team2MidLaneChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(8), champions));
            }
            if (team2JungleChamp != null) {
                team2JungleChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(7), champions));
            }
            if (team2ADCChamp != null) {
                team2ADCChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(6), champions));
            }
            if (team2SupportChamp != null) {
                team2SupportChamp.setBackgroundResource(getChampionIDFilePath(summoner.current_match._participants.get(5), champions));
            }
        }
    }

    public int getProfileIDFilePath(Summoner summoner) {

        String imageFileName = "r" + summoner.getProfileIconID();

        return MatchInfo.this.getResources().getIdentifier(imageFileName,
                "drawable", getPackageName());
    }

    public int getChampionIDFilePath(LiveGameParticipant summoner, ChampionIDContainer champions) {

        StringBuilder champion_name = new StringBuilder();
        champion_name.append(champions.champion_key_value.get(summoner._champion_ID));

        System.out.printf("ID 7: %s\n", champions.champion_key_value.get((long)7));

        int underscore_index = champion_name.indexOf("_");
        int apostrophe_index = champion_name.indexOf("'");

        if (underscore_index >= 0) {
            champion_name.deleteCharAt(underscore_index);
        }
        if (apostrophe_index >= 0) {
            champion_name.deleteCharAt(apostrophe_index);
        }
        System.out.printf("summoner.champion_ID: %s\nchampion_name: %s\n", summoner._champion_ID,
                champion_name.toString().toLowerCase());

        return MatchInfo.this.getResources().getIdentifier(champion_name.toString().toLowerCase(),
                "drawable", getPackageName());
    }

    //Displays a toast if there was an error for any of the API calls
    public void checkResponseCode(List<Integer> responseCodes) {
        RiotAPIToastManager toastManager = new RiotAPIToastManager(this);

        for (int i = 0; i < responseCodes.size(); i++) {
            toastManager.makeToast(responseCodes.get(i), i);
        }

    }
}
