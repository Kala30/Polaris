package com.polaris.polaris;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class GetData extends AsyncTask<String, Void, Void> {

    private Movie result;

    private Context context;
    private String url;

    public GetData(Context context, String imdbId) {
        this.context = context;
        this.url = "http://omdbapi.com/?apikey=72efb3e8&plot=full&i=" + imdbId;
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
                        // display response
                        //Log.d("Response", response.toString());
                        try {
                            result = new Movie(response.getString("Title"), response.getString("Released"));
                            result.plot = response.getString("Plot");
                            result.rated = response.getString("Rated");
                            result.posterURL = response.getString("Poster");
                            result.released = response.getString("Released").replaceFirst("^0+(?!$)", ""); // Remove leading zero
                            result.runtime = response.getString("Runtime");
                            result.genre = response.getString("Genre");
                            result.website = response.getString("Website");

                            JSONArray ratings = response.getJSONArray("Ratings");
                            String[] sources = new String[3];
                            String[] values = new String[3];
                            for (int i = 0; i < ratings.length(); i++) {
                                JSONObject rating = ratings.getJSONObject(i);
                                if (rating.getString("Source").equals("Internet Movie Database"))
                                    result.imdbScore = rating.getString("Value");
                                else if (rating.getString("Source").equals("Rotten Tomatoes"))
                                    result.rtScore = rating.getString("Value");
                                else if (rating.getString("Source").equals("Metacritic"))
                                    result.metaScore = rating.getString("Value");
                            }

                            final MovieDetailActivity activity = (MovieDetailActivity) context;

                            TextView info = activity.findViewById(R.id.info);
                            info.setText(result.rated + "\n" + result.released + "\n" + result.genre + "\n" + result.runtime);

                            TextView ratingsView = activity.findViewById(R.id.ratings);
                            String ratingString = "";
                            if (result.imdbScore != null)
                                ratingString += "Rotten Tomatoes: " + result.imdbScore + "\n";
                            else if (result.rtScore != null)
                                ratingString += "IMDB: " + result.imdbScore + "\n";
                            else if (result.metaScore != null)
                                ratingString += "Metacritic: " + result.metaScore;

                            ratingsView.setText(ratingString);

                            TextView description = activity.findViewById(R.id.description);
                            description.setText(result.plot);

                            Button button = activity.findViewById(R.id.site_button);
                            button.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(result.website));
                                    activity.startActivity(i);
                                }
                            });

                            CollapsingToolbarLayout toolbarLayout = activity.findViewById(R.id.toolbar_layout);
                            toolbarLayout.setTitle(result.title);

                            SetDrawable setDrawable = new SetDrawable(context, result.posterURL);
                            setDrawable.execute();
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

    class SetDrawable extends AsyncTask<Void, Void, Drawable> {
        private Context context;
        private String url;

        public SetDrawable(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        @Override
        public Drawable doInBackground(Void... params) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");

                // Resize bitmap
                Bitmap bm = ((BitmapDrawable)d).getBitmap();
                Bitmap bitmapResized = Bitmap.createScaledBitmap(bm, bm.getWidth()*2, bm.getHeight()*2, false);

                return new BitmapDrawable(context.getResources(), bitmapResized);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable result) {
            if (result != null) {
                MovieDetailActivity activity = (MovieDetailActivity) context;
                ((ImageView)activity.findViewById(R.id.poster)).setImageDrawable(result);
            }
        }

    }



}
