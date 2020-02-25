package com.example.leagueoflegendstheorycrafting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LiveGame implements Serializable {

        @SerializedName("gameId")
        long _gameId;

        @SerializedName("gameMode")
        String _game_mode;

        @SerializedName("mapId")
        int _mapId;

        @SerializedName("gameType")
        String _game_type;

        @SerializedName("participants")
        List<LiveGameParticipant> _participants;

        @SerializedName("bannedChampions")
        List<BannedChampions> _banned_champions;
}
