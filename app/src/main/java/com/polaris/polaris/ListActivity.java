package com.polaris.polaris;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;
    ArrayAdapter<Movie> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movieList = (ArrayList<Movie>) extras.get("list");
        }

        ListView listView = findViewById(R.id.list);
        adapter = new MovieAdapter(this, movieList);
        listView.setAdapter(adapter);
        adapter.addAll(movieList);
        adapter.notifyDataSetChanged();

    }

    class MovieAdapter extends ArrayAdapter<Movie> {
        public MovieAdapter(Context context, ArrayList<Movie> movies) {
            super(context, 0, movies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Movie movie = getItem(position);

            if (movie == null)
                return null;

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_item, parent, false);
            }
            // Lookup view for data population
            TextView title = convertView.findViewById(R.id.text1);
            TextView year = convertView.findViewById(R.id.text2);
            // Populate the data into the template view using the data object
            title.setText(movie.title);
            year.setText(movie.released);
            convertView.setTag(movie.imdbId);
            Glide.with(parent)
                    .load(movie.posterURL)
                    .into((ImageView) convertView.findViewById(R.id.icon));


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String imdbId = (String) v.getTag();
                    Intent intent = new Intent(v.getContext(), MovieDetailActivity.class);
                    intent.putExtra("imdb_id", imdbId);
                    v.getContext().startActivity(intent);
                }
            });
            // Return the completed view to render on screen
            return convertView;
        }

    }


}
