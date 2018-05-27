package com.polaris.polaris;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class SetImdbTag extends AsyncTask<String, Void, String>{

    private String tmdbId;
    private View view;

    public SetImdbTag (View view, String tmdbId) {
        this.view = view;
        this.tmdbId = tmdbId;
    }

    @Override
    protected String doInBackground(String... params) {
        URL endpoint;
        String result = null;
        try {
            endpoint = new URL("https://api.themoviedb.org/3/movie/" + tmdbId + "?api_key=e2819c94fb933c7210651b7eb6f65657");

            HttpsURLConnection urlConnection = (HttpsURLConnection) endpoint.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                InputStream responseBody = new BufferedInputStream(urlConnection.getInputStream());
                // Parser
                JsonParser jsonParser = new JsonParser();
                result = jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"))
                        .getAsJsonObject().get("imdb_id").getAsString();
                responseBody.close();
            }
            urlConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        //Log.d("SetImdbTag","IMDB ID: " + result);
        if (result != null) {
            view.setTag(result);
        }
    }
}
