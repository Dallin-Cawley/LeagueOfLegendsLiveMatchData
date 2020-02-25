package com.example.leagueoflegendstheorycrafting;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Summoner implements Serializable{

    private int summoner_v4_response_code;
    private int league_v4_response_code;
    private int spectator_v4_response_code;


    //For the list of ranks returned from the League-v4 request
    private Type _ranked_type;
    public Collection<RankedInfo> collections_queue_ranks;
    public List<RankedInfo> queue_ranks;

    //Holds the Live Game info of the summoner
    LiveGame current_match;

    //Variables for Summoner-v4 Riot API Response
    @SerializedName("id")
    private String summoner_ID;

    @SerializedName("accountId")
    private String account_ID;

    @SerializedName("puuid")
    private String pu_ID;

    @SerializedName("name")
    private String summoner_name;

    @SerializedName("summonerLevel")
    private int summoner_lvl;

    @SerializedName("profileIconId")
    private String profile_icon_ID;




    public Summoner(Summoner new_summoner) {
        this.account_ID = new_summoner.account_ID;
        this.profile_icon_ID = new_summoner.profile_icon_ID;
        this.pu_ID = new_summoner.pu_ID;
        this.summoner_ID = new_summoner.summoner_ID;
        this.summoner_name = new_summoner.summoner_name;
        this.summoner_lvl = new_summoner.summoner_lvl;

        this._ranked_type = new TypeToken<Collection<RankedInfo>>(){}.getType();
        this.collections_queue_ranks = new ArrayList<>();
        this.queue_ranks = new ArrayList<>();

    }

    public void addSummonerAPICall(Summoner adding_summoner) {
        if (this.summoner_name == null && adding_summoner.summoner_name != null) {
            this.summoner_name = adding_summoner.summoner_name;
        }
        if (this.summoner_ID == null && adding_summoner.summoner_ID != null) {
            this.summoner_ID = adding_summoner.summoner_ID;
        }
        if (this.account_ID == null && adding_summoner.account_ID != null) {
            this.account_ID = adding_summoner.account_ID;
        }
        if (this.profile_icon_ID == null && adding_summoner.profile_icon_ID != null) {
            this.profile_icon_ID = adding_summoner.profile_icon_ID;
        }
        if (this.pu_ID == null && adding_summoner.pu_ID != null) {
            this.pu_ID = adding_summoner.pu_ID;
        }
        if (this.summoner_lvl < 0 && adding_summoner.summoner_lvl >= 0) {
            this.summoner_lvl = adding_summoner.summoner_lvl;
        }

    }

    public void addRankedInfo(Gson json_manip, String json_ranked_info) {
        if (json_ranked_info == null) {
            System.out.println("\nJson_ranked_info is null");
        }
        else if (json_manip == null) {
            System.out.println("\nJson_manip is null");
        }
        else if (_ranked_type == null) {
            System.out.println("\n Ranked_type is null");
        }
        collections_queue_ranks = json_manip.fromJson(json_ranked_info, _ranked_type);
        if (collections_queue_ranks == null) {
            System.out.println("\n Collections_queue_rankes is null");
        }
        queue_ranks.addAll(collections_queue_ranks);
    }

    public void addLiveGame(Gson json_manip, String json_live_game_info) {
        this.current_match = json_manip.fromJson(json_live_game_info, LiveGame.class);
    }

    /* Setters */
    public void setSummonerV4ResponseCode(int summonerV4ResponseCode) {
        summoner_v4_response_code = summonerV4ResponseCode;
    }

    public void setLeagueV4ResponseCode(int leagueV4ResponseCode) {
        league_v4_response_code = leagueV4ResponseCode;
    }

    public void setSpectatorV4ResponseCode(int spectatorV4ResponseCode) {
        spectator_v4_response_code = spectatorV4ResponseCode;
    }

    /* Getters */
    public String getSummonerID() {
        return summoner_ID;
    }

    public String getSummonerName() {
        return summoner_name;
    }

    public int getSummonerLvL() {
        return summoner_lvl;
    }

    public int getSummonerV4ResponseCode() {
        return summoner_v4_response_code;
    }

    public int getLeagueV4ResponseCode() {
        return league_v4_response_code;
    }

    public int getSpectatorV4ResponseCode() {
        return spectator_v4_response_code;
    }

    public String getProfileIconID() {
        return profile_icon_ID;
    }

    boolean isInGame() {

        if (current_match != null) {
            return true;
        }

        return false;
    }

}
