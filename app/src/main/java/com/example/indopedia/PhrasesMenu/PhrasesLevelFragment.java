package com.example.indopedia.PhrasesMenu;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indopedia.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PhrasesLevelFragment extends Fragment {
    //tag name
    private static final String TAG = "PhrasesLevelFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Levels> mLevelList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    @Override
    public void onResume() {
        //stub
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.layout_phrases_level_recycler, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.basic_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PhrasesLevelAdapter(mLevelList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initializeData() {
        try {
            loadArticles();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    loadArticles();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
    }

    private void loadArticles() throws IOException {
        mLevelList = new ArrayList<>();
        Log.d(TAG, "Loading mLevelList...");

        final Resources resources = getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.levels);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String packageName = getContext().getPackageName();

        try {
            String line;
            //read until end of file
            while ((line = reader.readLine()) != null) {
                //split by -
                String[] strings = TextUtils.split(line, "-");
                //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>

                if (strings.length < 2) continue;

                String levelTitle = strings[0].trim();
                int photoId = resources.getIdentifier(strings[1].trim(), "drawable", packageName);

                //Log.d(TAG, "Drawable : " + photoId);

                Log.d(TAG, "Loading levels for " + levelTitle);

                mLevelList.add(new Levels(levelTitle, photoId));
            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "Finished loading mArticleList.");
    }
}
