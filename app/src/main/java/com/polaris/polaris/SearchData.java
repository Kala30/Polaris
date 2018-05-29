package com.polaris.polaris;

        import android.content.Context;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.util.Log;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class SearchData extends AsyncTask<String, Void, Void> {

    private Movie result;

    private Context context;
    private String url;

    public SearchData(Context context, String title, String year) {
        this.context = context;
        this.url = "http://omdbapi.com/?apikey=72efb3e8&type=movie&s=" + title + "&y=" + year;
    }

    @Override
    protected Void doInBackground(String... params) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<Movie> results = new ArrayList<>();
                            JSONArray movies = response.getJSONArray("Search");
                            for (int i = 0; i < movies.length(); i++) {
                                JSONObject currentMovie = movies.getJSONObject(i);
                                Movie movie = new Movie(currentMovie.getString("Title"), currentMovie.getString("Year"));
                                movie.posterURL = currentMovie.getString("Poster");
                                movie.imdbId = currentMovie.getString("imdbID");
                                results.add(movie);
                            }

                            Intent intent = new Intent(context, ListActivity.class);
                            intent.putExtra("list", results);
                            context.startActivity(intent);

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
