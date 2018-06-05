package com.polaris.polaris;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    ArrayList<Movie> movieList;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movieList = (ArrayList<Movie>) extras.get("list");
            if (extras.get("title") != null)
                setTitle((String)extras.get("title"));
        }

        ListView listView = findViewById(R.id.list);
        adapter = new MovieAdapter(this, movieList);
        listView.setAdapter(adapter);
        adapter.addAll(movieList);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String imdbId = (String) view.getTag();
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("imdb_id", imdbId);
                view.getContext().startActivity(intent);
            }
        });
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
                //convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            // Lookup view for data population
            TextView title = convertView.findViewById(R.id.text1);
            TextView info = convertView.findViewById(R.id.text2);
            TextView description = convertView.findViewById(R.id.text3);
            
            // Populate the data into the template view using the data object
            title.setText(movie.title);
            info.setText(movie.released);
            description.setText(movie.shortDesc);
            convertView.setTag(movie.imdbId);
            Glide.with(parent)
                    .load(movie.posterURL)
                    .into((ImageView) convertView.findViewById(R.id.icon));

            // Return the completed view to render on screen
            return convertView;
        }

    }


}
