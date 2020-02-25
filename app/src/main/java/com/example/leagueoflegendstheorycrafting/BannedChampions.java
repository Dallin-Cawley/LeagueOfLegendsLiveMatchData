package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BannedChampions implements Serializable {

    @SerializedName("pickTurn")
    int _turn_picked;

    @SerializedName("championId")
    long _champion_ID;

    @SerializedName("teamId")
    long _banning_team_ID;
}
