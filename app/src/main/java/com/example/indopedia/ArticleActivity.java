package com.example.indopedia;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

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
        mThingsToDo.setText("Things To Do");

        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TTDAdapter(mThingsToDoList);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initializeData() {

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
                //split by -
                String[] strings = TextUtils.split(line, "---");
                //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>

                //if (strings.length < 3) continue;

                int photoId = resources.getIdentifier(strings[0].trim(), "drawable", packageName);
                String title = strings[1].trim();
                String description = strings[2].trim();


                Log.d(TAG, "Drawable : " + photoId);

                Log.d(TAG, "Loading article for " + articleTitle);

                for (int x =0; x<10; x++){
                    mThingsToDoList.add(new ThingsToDo(photoId, title, description));
                }
            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "Finished loading list.");
    }

}
