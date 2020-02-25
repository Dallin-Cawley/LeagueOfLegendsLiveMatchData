package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveGameParticipant implements Serializable {
    @SerializedName("profileIconId")
    double _profile_icon_ID;

    @SerializedName("championId")
    long _champion_ID;

    @SerializedName("summonerName")
    String _summoner_name;

    @SerializedName("summonerId")
    String _summoner_ID;

    @SerializedName("spell1ID")
    long _spell_1_ID;

    @SerializedName("spell2ID")
    long _spell_2_ID;

    @SerializedName("teamId")
    long _team_ID;

    @SerializedName("bot")
    boolean _is_bot;
}
