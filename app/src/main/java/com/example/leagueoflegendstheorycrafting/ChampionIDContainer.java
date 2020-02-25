package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ChampionIDContainer {

    @SerializedName("Champions")
    Map<Long, String> champion_key_value;
}
