package com.polaris.polaris;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

        ((TextView)findViewById(R.id.now_playing_list).findViewById(R.id.title_text)).setText("Now Playing");
        ((TextView)findViewById(R.id.popular_list).findViewById(R.id.title_text)).setText("Popular");
        ((TextView)findViewById(R.id.top_rated_list).findViewById(R.id.title_text)).setText("Top Rated");
        ((TextView)findViewById(R.id.upcoming_list).findViewById(R.id.title_text)).setText("Upcoming");

        new GetListData(this, (LinearLayout) findViewById(R.id.now_playing_list).findViewById(R.id.list), "now_playing").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.popular_list).findViewById(R.id.list), "popular").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.top_rated_list).findViewById(R.id.list), "top_rated").execute();
        new GetListData(this, (LinearLayout) findViewById(R.id.upcoming_list).findViewById(R.id.list), "upcoming").execute();
    }

    public void onFabClick(View view) {
        final Context context = this;
        final View dialogView = this.getLayoutInflater().inflate(R.layout.activity_search, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Search")
                .setView(dialogView)
                .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etMovieTitle = (EditText) dialogView.findViewById(R.id.etMovieTitle);
                        String title = etMovieTitle.getText().toString();
                        EditText etYear = (EditText) dialogView.findViewById(R.id.etYear);
                        String year = etYear.getText().toString();

                        SearchData searchData = new SearchData(context, title, year);
                        searchData.execute();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

}
