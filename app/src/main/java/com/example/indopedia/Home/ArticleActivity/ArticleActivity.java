package com.example.indopedia.Home.ArticleActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indopedia.Home.HomeFragment.HomeAdapter;
import com.example.indopedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by asus on 06/12/2017.
 */

public class ArticleActivity extends AppCompatActivity {

    private static final String TAG = "ArticleActivity";

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private ObservableScrollView mScrollView;


    private String articleBackEndName;
    private ImageView mArticleHeader;
    private TextView mArticleText;

    private TextView mTouristTextView;

    //Things To Do
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<TouristDestination> mTouristDestinationList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        mToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        final ColorDrawable cd = new ColorDrawable(Color.rgb(68, 74, 83));
        cd.setAlpha(0);
        mActionBar.setBackgroundDrawable(cd);

        mScrollView = findViewById(R.id.article_scroll_view);
        mScrollView.setOnScrollViewListener(new ObservableScrollView.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ObservableScrollView v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = 650;
                if (scrollY > maxDist) {
                    return 255;
                } else if (scrollY < minDist) {
                    return 0;
                } else {
                    int alpha = 0;
                    alpha = (int) ((255.0 / maxDist) * scrollY);
                    return alpha;
                }
            }
        });

        mArticleHeader = findViewById(R.id.article_header);
        mArticleText = findViewById(R.id.article_text);
        mTouristTextView = findViewById(R.id.article_tourist_destinations);

        //Use data from Article class
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        articleBackEndName = extras.getString(HomeAdapter.ARTICLE_BACKEND);

        setTitle(extras.getString(HomeAdapter.ARTICLE_TITLE));
        mArticleHeader.setImageResource(extras.getInt(HomeAdapter.ARTICLE_HEADER));
        mArticleText.setText(extras.getString(HomeAdapter.ARTICLE_TEXT));

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        //load data from .txt files. Returns true if successful
        boolean success = false;
        try {
            success = load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //If failed to load data, don't show Tourist Destinations
        if (success) {
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setNestedScrollingEnabled(false);
            //Grid layout with 2 columns
            mLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);

            int spanCount = 2; // 2 columns
            int spacing = 20; // 50px
            boolean includeEdge = false;
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            mAdapter = new TDAdapter(mTouristDestinationList, getFragmentManager());
            mAdapter.setHasStableIds(true);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mTouristTextView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private boolean load() throws IOException {
        mTouristDestinationList = new ArrayList<>();
        Log.d(TAG, "Loading mTouristDestinationList...");

        StringBuilder sb = new StringBuilder();
        sb.append(articleBackEndName.replaceAll(" ", "_").toLowerCase());
        sb.append("_td");

        final Resources resources = this.getResources();

        String packageName = this.getPackageName();
        int rawId = resources.getIdentifier(sb.toString(), "raw", packageName);

        //If there is no "Tourist Destinations" resource file defined for this particular article
        //Remove the corresponding TextView and RecyclerView
        if (rawId != 0) {
            InputStream inputStream = resources.openRawResource(rawId);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {

                String line;
                //read until end of file
                while ((line = reader.readLine()) != null) {
                    //split by ---
                    String[] strings = TextUtils.split(line, "---");

                    //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                    //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>
                    if (strings.length < 3) continue;

                    int photoId = resources.getIdentifier(strings[0].trim(), "drawable", packageName);
                    String title = strings[1].trim();
                    String description = strings[2].trim();

                    Log.d(TAG, "Loading article for " + title);

                    mTouristDestinationList.add(new TouristDestination(photoId, title, description));
                }
            } finally {
                reader.close();
            }
            Log.d(TAG, "Finished loading list.");
            return true;
        } else {
            return false;
        }
    }

}
