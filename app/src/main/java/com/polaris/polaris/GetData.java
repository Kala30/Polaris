package com.polaris.polaris;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetData extends AsyncTask<String, Void, Void> {

    private Movie result;

    private Context context;
    private String title;
    private String year;

    public GetData(Context context, String title, String year) {
        this.context = context;
        this.title = title;
        this.year = year;
    }

    @Override
    protected Void doInBackground(String... params) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        //String url = "http://www.theimdbapi.org/api/find/movie?title=transformers&year=2007";
        String url = "http://omdbapi.com/?i=tt3896198&apikey=72efb3e8";

        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        try {
                            result = new Movie(response.getString("Title"), response.getString("Released"));
                            result.plot = response.getString("Plot");
                            TextView description = ((MainActivity)context).findViewById(R.id.description);
                            description.setText(result.plot);
                        } catch (JSONException e) {
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
}
