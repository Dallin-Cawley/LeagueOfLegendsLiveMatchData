package com.example.leagueoflegendstheorycrafting;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor;

public class RiotAPIConnection extends HTTPConnection{
    List<String> _url_parameters;


    RiotAPIConnection() {
        super("https://na1.api.riotgames.com", "");
    }
    RiotAPIConnection(AppCompatActivity reference_to) throws IOException {

        StringBuilder api_key = new StringBuilder();
        try {
            Scanner read_file = new Scanner(new MatchInfo().getAssets().open("RiotAPIKey.txt"));
            while (read_file.hasNext()) {
                api_key.append(read_file.nextLine());
            }
        }
        catch(IOException i) {
            System.out.println(i);
        }

        setBaseUrl("https://na1.api.riotgames.com");
        setApiKey(api_key.toString());
        _url_parameters = new ArrayList<>();

    }

    public String summonerInfoByName(String summoner_name) throws IOException {
        String url_addition = "/lol/summoner/v4/summoners/by-name/";
        summoner_name += "/";

        if (!_url_parameters.isEmpty()){
            _url_parameters.clear();
        }

        _url_parameters.add(url_addition);
        _url_parameters.add(summoner_name);

        establishConnection(_url_parameters, "GET");

        if (getResponseCode() == 401) {
            System.out.print("You are unauthorized");

        }

        System.out.println("Response Code: " + getResponseCode());

        return getResponse();
    }

    public String liveGameBySummonerID(String summoner_ID) throws IOException {
        String url_addition = "/lol/spectator/v4/active-games/by-summoner/";

        if (!_url_parameters.isEmpty()) {
            _url_parameters.clear();
        }

        _url_parameters.add(url_addition);
        _url_parameters.add(summoner_ID);

        establishConnection(_url_parameters, "GET");

        return getResponse();
    }

    public String liveGameBySummonerName(String summoner_name) throws IOException {
        String json_response = summonerInfoByName(summoner_name);
        Gson json_manip = new Gson();
        Summoner summoner = new Summoner (json_manip.fromJson(json_response, Summoner.class));

        return liveGameBySummonerID(summoner.getSummonerID());

    }

    public String currentRankBySummonerID(String summoner_ID) throws IOException {
        String url_addition = "/lol/league/v4/entries/by-summoner/";

        if (!_url_parameters.isEmpty()) {
            _url_parameters.clear();
        }

        _url_parameters.add(url_addition);
        _url_parameters.add(summoner_ID);

        establishConnection(_url_parameters, "GET");


        return getResponse();
    }
}