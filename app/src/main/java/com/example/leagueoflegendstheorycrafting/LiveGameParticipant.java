package com.example.leagueoflegendstheorycrafting;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    //For the list of ranks returned from the League-v4 request
    private Type ranked_type = new TypeToken<Collection<RankedInfo>>(){}.getType();
    public Collection<RankedInfo> collections_queue_ranks = new ArrayList<>();
    public List<RankedInfo> queue_ranks = new ArrayList<>();


    public void setRank(Gson json_manip, String rank_json) {
        collections_queue_ranks = json_manip.fromJson(rank_json, ranked_type);
        queue_ranks.addAll(collections_queue_ranks);
    }

    public List<RankedInfo> getRank() {
        return queue_ranks;
    }

}
