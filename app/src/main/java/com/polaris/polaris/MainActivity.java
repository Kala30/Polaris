package com.polaris.polaris;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Associate searchable configuration with the SearchView
                /*SearchManager searchManager =
                        (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                SearchView searchView =
                        (SearchView) menu.findItem(R.id.search).getActionView();
                searchView.setSearchableInfo(
                        searchManager.getSearchableInfo(getComponentName()));*/
                Toast.makeText(getBaseContext(), "Search", Toast.LENGTH_SHORT).show();
            }
        });

        ((TextView)findViewById(R.id.now_playing_list).findViewById(R.id.title_text)).setText("Now Playing");
        ((TextView)findViewById(R.id.popular_list).findViewById(R.id.title_text)).setText("Popular");
        ((TextView)findViewById(R.id.top_rated_list).findViewById(R.id.title_text)).setText("Top Rated");
        ((TextView)findViewById(R.id.upcoming_list).findViewById(R.id.title_text)).setText("Upcoming");

        new GetListData(this, (LinearLayout) findViewById(R.id.now_playing_list).findViewById(R.id.list), "now_playing").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.popular_list).findViewById(R.id.list), "popular").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.top_rated_list).findViewById(R.id.list), "top_rated").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.upcoming_list).findViewById(R.id.list), "upcoming").execute();
    }
}
