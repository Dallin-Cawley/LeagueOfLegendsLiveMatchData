package com.example.leagueoflegendstheorycrafting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;


public abstract class HTTPConnection {

    String _BASE_URL;
    private String _API_KEY;
    HttpURLConnection _connection;
    URL _connection_url;

    HTTPConnection(String base_url, String api_key) {
        _BASE_URL = base_url;
        _API_KEY = api_key;
        _connection = null;
        _connection_url = null;
    }

    protected HTTPConnection() {
        _BASE_URL = "";
        _API_KEY = "";
        _connection = null;
        _connection_url = null;
    }


    public void setBaseUrl(String base_url) {
       _BASE_URL = base_url;
    }
    public void setApiKey(String api_key) {
        _API_KEY = api_key;
    }

    private String buildURL(List<String>url_parameters) {

        StringBuilder complete_url = new StringBuilder(_BASE_URL);

        for (int i = 0; i < url_parameters.size(); i++) {
            complete_url.append(url_parameters.get(i));
        }

        complete_url.append(_API_KEY);

        return complete_url.toString();
    }

    public void establishConnection(List<String>url_parameters, String requestType) {
        try {
            _connection_url = new URL (buildURL(url_parameters));
            _connection = (HttpURLConnection) _connection_url.openConnection();

            setRequestType(requestType);
        }
        catch (IOException exception) {
            System.out.print(exception);
        }
    }

    public int getResponseCode() throws IOException {
        return _connection.getResponseCode();
    }

    public void setRequestType(String request_type) throws ProtocolException {
        _connection.setRequestMethod(request_type);
    }

    public String getResponse() throws IOException {
        if (_connection.getRequestMethod().equals("GET")) {
            if (_connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                //Get response from our connection
                BufferedReader connection_response = new BufferedReader(
                        new InputStreamReader(_connection.getInputStream()));

                //Used to convert buffer to string
                String connection_response_line = "";
                StringBuilder response = new StringBuilder();

                //While there are lines in our connection_response, assign it to our string variable
                //and insert it into your StringBuffer.
                while ((connection_response_line = connection_response.readLine()) != null) {
                    response.append(connection_response_line);
                }

                return response.toString();
            }
            else {
                System.out.print("Connection unsuccessful\n");
            }
        }
        else {
            System.out.print("Request method not set to 'GET'");
        }
        return null;
    }
}
