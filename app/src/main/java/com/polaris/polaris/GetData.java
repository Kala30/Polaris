package com.polaris.polaris;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

public class GetData extends AsyncTask<String, Void, Movie> {

    private Context context;
    private String title;
    private String year;

    public GetData(Context context, String title, String year) {
        this.context = context;
        this.title = title;
        this.year = year;
    }

    @Override
    protected Movie doInBackground(String... params) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://www.theimdbapi.org/api/find/movie?title=transformers&year=2007";

        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", response);
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    protected void onPostExecute(Movie result) {

    }
}
