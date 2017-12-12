package com.example.indopedia.Home.ArticleActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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
    private Drawable mActionBarBackgroundDrawable;

    private String articleTitle;
    private ImageView mArticleHeader;
    private TextView mArticleText;
    private TextView mThingsToDo;

    //Things To Do
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ThingsToDo> mThingsToDoList;

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

        ObservableScrollView scrollView = findViewById(R.id.article_scroll_view);
        scrollView.setOnScrollViewListener(new ObservableScrollView.OnScrollViewListener() {

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
        mThingsToDo = findViewById(R.id.article_things_to_do);

        //Use data from Article class
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        articleTitle = extras.getString(HomeAdapter.ARTICLE_TITLE);
        setTitle(articleTitle);
        mArticleHeader.setImageResource(extras.getInt(HomeAdapter.ARTICLE_HEADER));
        mArticleText.setText(extras.getString(HomeAdapter.ARTICLE_TEXT));

        initializeRecyclerView();
    }

    private void initializeRecyclerView() {

        initializeRecyclerViewDataset();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        //Grid layout with 2 columns
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        int spanCount = 2; // 2 columns
        int spacing = 20; // 50px
        boolean includeEdge = false;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));

        //allow textview to be scrolled inside
//        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                findViewById(R.id.scroll_view).getParent().requestDisallowInterceptTouchEvent(false);
//                return false;
//            }
//        });
        // specify an adapter (see also next example)
        mAdapter = new TTDAdapter(mThingsToDoList, getFragmentManager());
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initializeRecyclerViewDataset() {

        new Thread(new Runnable() {
            public void run() {
                try {
                    load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    private void load() throws IOException {
        mThingsToDoList = new ArrayList<>();
        Log.d(TAG, "Loading mThingsToDoList...");

        StringBuilder sb = new StringBuilder();
        sb.append(articleTitle.replaceAll(" ", "_").toLowerCase());
        sb.append("_ttd");

        final Resources resources = this.getResources();

        String packageName = this.getPackageName();
        int rawId = resources.getIdentifier(sb.toString(), "raw", packageName);

        InputStream inputStream = resources.openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try{
            String line;
            //read until end of file
            while ((line = reader.readLine()) != null){
                //split by ---
                String[] strings = TextUtils.split(line, "---");

                //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>
                if (strings.length < 3) continue;

                int photoId = resources.getIdentifier(strings[0].trim(), "drawable", packageName);
                String title = strings[1].trim();
                String description = strings[2].trim();


                Log.d(TAG, "Drawable : " + photoId);

                Log.d(TAG, "Loading article for " + articleTitle);

                mThingsToDoList.add(new ThingsToDo(photoId, title, description));
            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "Finished loading list.");
    }

}
