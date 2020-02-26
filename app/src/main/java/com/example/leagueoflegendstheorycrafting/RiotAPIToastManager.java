package com.example.leagueoflegendstheorycrafting;

import android.content.Context;
import android.widget.Toast;

public class RiotAPIToastManager {
    private Toast toaster;
    private String requestType;
    private Context activityContext;

    RiotAPIToastManager(Context activityContext) {
        this.activityContext = activityContext;

    }

    public void makeToast(int responseCode, int request) {

        switch (request) {
            case 0:
                requestType = "Summoner Info";
                break;
            case 1:
                requestType = "Summoner Ranked Info";
                break;
            case 2:
                requestType = "Live Game Info";
                break;

        }

        toaster = Toast.makeText(activityContext,"Connection Successful\n" + requestType, Toast.LENGTH_SHORT);

        switch (responseCode) {
            case 400:
                toaster.setText("Bad Request: HTTP request may be broken\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 401:
                toaster.setText("Unauthorized: Have you updated the API Key?\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 403:
                toaster.setText("Forbidden: Invalid request or invalid API key\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 404:
                toaster.setText("Data not Found:\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 405:
                toaster.setText("Method Not Allowed: Riot games no longer supports\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 415:
                toaster.setText("Unsupported Media Type: The body of the request is not set up " +
                        "properly\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 429:
                toaster.setText("Rate Limit Exceeded:\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 500:
                toaster.setText("Internal Server Error: Please try again\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 502:
                toaster.setText("Bad Gateway:\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 503:
                toaster.setText("Service Unavailable: Please try again later\n" + requestType);
                toaster.setDuration(Toast.LENGTH_SHORT);
                toaster.show();
                break;
            case 504:
                toaster.setText("Gateway Timeout: Please check your internet connection" +
                        " and try again\n" + requestType);
                toaster.setDuration(Toast.LENGTH_LONG);
                toaster.show();
                break;
        }
    }
}
