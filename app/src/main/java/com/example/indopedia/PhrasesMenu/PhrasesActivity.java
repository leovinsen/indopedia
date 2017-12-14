package com.example.indopedia.PhrasesMenu;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.example.indopedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Velcone on 12/5/2017.
 */

public class PhrasesActivity extends AppCompatActivity {

    private static final String TAG = "PhrasesDatabase";

    private String levelTitle;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Phrases> phrases;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phrases_recycler_card_view);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        levelTitle = extras.getString(PhrasesLevelAdapter.LEVEL_TITLE);

        initializeData();
        mRecyclerView = (RecyclerView) findViewById(R.id.phrases_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PhrasesAdapter(phrases);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initializeData() {
        try {
            loadWords();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    loadWords();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
    }

    private void loadWords() throws IOException {
        phrases = new ArrayList<>();
        Log.d(TAG, "Loading words...");
        final Resources resources = this.getResources();
        String packageName = this.getPackageName();
        int rawId = resources.getIdentifier(levelTitle.toLowerCase(), "raw", packageName);
        InputStream inputStream = resources.openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = TextUtils.split(line, "-");
                if (strings.length < 2) {
                    continue;
                }
                String indoWords = strings[0].trim();
                String engWords = strings[1].trim();
                int audioID = resources.getIdentifier(strings[2].trim(), "raw", packageName);
                phrases.add(new Phrases(indoWords, engWords, audioID));

            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "DONE loading phrases.");
    }


}
