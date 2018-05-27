package com.polaris.polaris;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class GetListData extends AsyncTask<String, Void, Void> {

    private Context context;
    private LinearLayout listLayout;

    // Can be "now_playing", "popular", "top_rated", "upcoming"
    private String query;


    public GetListData(Context context, LinearLayout listLayout, String query) {
        this.context = context;
        this.listLayout = listLayout;
        this.query = query;
    }

    @Override
    protected Void doInBackground(String... params) {

        String url = "https://api.themoviedb.org/3/movie/" + query + "?api_key=e2819c94fb933c7210651b7eb6f65657";

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //create LayoutInflator class
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                            JSONArray results = response.getJSONArray("results");
                            int size = results.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject currentMovie = results.getJSONObject(i);
                                // create dynamic LinearLayout and set Image on it.
                                if (currentMovie != null) {
                                    final RelativeLayout clickableColumn = (RelativeLayout) inflater.inflate(R.layout.list_column, null);
                                    ImageView thumbnail = clickableColumn.findViewById(R.id.thumbnail_image);

                                    SetDrawable setDrawable = new SetDrawable(thumbnail, "https://image.tmdb.org/t/p/w200" + currentMovie.getString("poster_path"));
                                    setDrawable.execute();

                                    SetImdbTag setImdbTag = new SetImdbTag((View)clickableColumn, currentMovie.getString("id"));
                                    setImdbTag.execute();

                                    clickableColumn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String imdbId = (String) v.getTag();
                                            //Log.d("column onClick", "IMDB ID: " + imdbId);
                                            Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                                            intent.putExtra("imdb_id", imdbId);
                                            v.getContext().startActivity(intent);
                                        }
                                    });

                                    listLayout.addView(clickableColumn);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", "Error");
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(getRequest);
        return null;
    }

    private String getImdbId(String tmdbId) throws IOException {
        URL endpoint = new URL("https://api.themoviedb.org/3/movie/" + tmdbId);

        HttpsURLConnection urlConnection = (HttpsURLConnection) endpoint.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        if (urlConnection.getResponseCode() == 200) {
            InputStream responseBody = new BufferedInputStream(urlConnection.getInputStream());
            // Parser
            JsonParser jsonParser = new JsonParser();
            return jsonParser.parse(new InputStreamReader(responseBody, "UTF-8"))
                    .getAsJsonObject().get("imdb_id").getAsString();
        } else
            return null;
    }
}

