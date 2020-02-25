package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RankedInfo implements Serializable {
    //Variables for League-v4 Riot API Response
    @SerializedName("queueType")
    public String _queue_type;

    @SerializedName("hotStreak")
    public boolean _hot_streak;

    @SerializedName("wins")
    public int _wins = -1;

    @SerializedName("losses")
    public int _losses = -1;

    @SerializedName("rank")  // eg I, II, IV, etc...
    public String _rank;

    @SerializedName("leagueId")
    public String _league_ID;

    @SerializedName("tier")   // eg Bronze, Silver, Challenger, etc...
    public String _tier;

    @SerializedName("leaguePoints")
    public int _league_points = -1;

    private RankedInfo() {
        _queue_type = null;
        _league_ID = null;
        _hot_streak = false;
        _rank = null;
        _tier = null;

        _league_points = -1;
        _wins = -1;
        _losses = -1;
    }

    public RankedInfo(RankedInfo adding_ranked_info) {
        if (this._queue_type == null && adding_ranked_info._queue_type != null) {
            this._queue_type = adding_ranked_info._queue_type;
        }

        /* _hot_streak is a boolean. No real way to check this one. */
        this._hot_streak = adding_ranked_info._hot_streak;

        if (this._wins < 0 && adding_ranked_info._wins >= 0) {
            this._wins = adding_ranked_info._wins;
        }
        if (this._losses < 0 && adding_ranked_info._losses >= 0) {
            this._losses = adding_ranked_info._losses;
        }
        if (this._rank == null && adding_ranked_info._rank != null) {
            this._rank = adding_ranked_info._rank;
        }
        if (this._league_ID == null && adding_ranked_info._league_ID != null) {
            this._league_ID = adding_ranked_info._league_ID;
        }
        if (this._tier == null && adding_ranked_info._tier != null) {
            this._tier = adding_ranked_info._tier;
        }
        if (this._league_points < 0 && adding_ranked_info._league_points >= 0) {
            this._league_points = adding_ranked_info._league_points;
        }

    }
}
