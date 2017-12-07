package com.example.indopedia;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    //tag name
    private static final String TAG = "HomeFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Article> mArticleList;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initializeData();
    }

    @Override
    public void onResume(){
        //stub
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new HomeAdapter(mArticleList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    private void initializeData() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    loadArticles();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void loadArticles() throws IOException {
        mArticleList = new ArrayList<>();
        Log.d(TAG, "Loading mArticleList...");

        final Resources resources = getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.articles);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String packageName = getContext().getPackageName();

        try{
            String line;
            //read until end of file
            while ((line = reader.readLine()) != null){
                //split by -
                String[] strings = TextUtils.split(line, "---");
                //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>

                if (strings.length < 3) continue;

                String articleTitle = strings[0].trim();
                String articleContent = strings[2].trim();
                int photoId = resources.getIdentifier(strings[1].trim(), "drawable", packageName);

                //Log.d(TAG, "Drawable : " + photoId);

                Log.d(TAG, "Loading article for " + articleTitle);

                mArticleList.add(new Article(articleTitle, articleContent, photoId));
            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "Finished loading mArticleList.");
    }
}
