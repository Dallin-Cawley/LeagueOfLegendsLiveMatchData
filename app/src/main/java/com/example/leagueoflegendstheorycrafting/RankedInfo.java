package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RankedInfo implements Serializable {
    //Variables for League-v4 Riot API Response
    @SerializedName("queueType")
    public String queue_type;

    @SerializedName("hotStreak")
    public boolean hot_streak;

    @SerializedName("wins")
    public int wins = -1;

    @SerializedName("losses")
    public int losses = -1;

    @SerializedName("rank")  // eg I, II, IV, etc...
    public String rank;

    @SerializedName("leagueId")
    public String league_ID;

    @SerializedName("tier")   // eg Bronze, Silver, Challenger, etc...
    public String tier;

    @SerializedName("leaguePoints")
    public int league_points = -1;

    private RankedInfo() {
        queue_type = null;
        league_ID = null;
        hot_streak = false;
        rank = null;
        tier = null;

        league_points = -1;
        wins = -1;
        losses = -1;
    }

    public RankedInfo(RankedInfo adding_ranked_info) {
        if (this.queue_type == null && adding_ranked_info.queue_type != null) {
            this.queue_type = adding_ranked_info.queue_type;
        }

        /* _hot_streak is a boolean. No real way to check this one. */
        this.hot_streak = adding_ranked_info.hot_streak;

        if (this.wins < 0 && adding_ranked_info.wins >= 0) {
            this.wins = adding_ranked_info.wins;
        }
        if (this.losses < 0 && adding_ranked_info.losses >= 0) {
            this.losses = adding_ranked_info.losses;
        }
        if (this.rank == null && adding_ranked_info.rank != null) {
            this.rank = adding_ranked_info.rank;
        }
        if (this.league_ID == null && adding_ranked_info.league_ID != null) {
            this.league_ID = adding_ranked_info.league_ID;
        }
        if (this.tier == null && adding_ranked_info.tier != null) {
            this.tier = adding_ranked_info.tier;
        }
        if (this.league_points < 0 && adding_ranked_info.league_points >= 0) {
            this.league_points = adding_ranked_info.league_points;
        }

    }

    public void setQueueType(String queue_type) {
        this.queue_type = queue_type;
    }
}
