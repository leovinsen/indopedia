package com.example.indopedia.CuisineMenu;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by asus on 06/12/2017.
 */

public class CuisineFragment extends Fragment {

    private static final String TAG = "CuisineFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Cuisine> mCuisineList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeData();
    }

    private void initializeData() {
        try {
            loadCuisine();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    loadCuisine();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }).start();
    }

    private void loadCuisine() throws IOException {
        mCuisineList = new ArrayList<>();
        Log.d(TAG, "Loading mCuisineList...");

        final Resources resources = getContext().getResources();
        InputStream inputStream = resources.openRawResource(R.raw.cuisine);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String packageName = getContext().getPackageName();

        try {
            String line;
            //read until end of file
            while ((line = reader.readLine()) != null) {
                //split by -
                String[] strings = TextUtils.split(line, "---");
                //terminate current iteration if article is not in the form of <TITLE - IMAGEID - CONTENT>
                //although form is <TITLE, IMAGEID, CONTENT>, they are inserted as <TITLE, CONTENT, IMAGEID>

                if (strings.length < 2) continue;

                int photoId = resources.getIdentifier("cuisine_" + strings[0].trim(), "drawable", packageName);
                String name = strings[1].trim();

                Log.d(TAG, "Loading food " + name);

                mCuisineList.add(new Cuisine(name, photoId));
            }
        } finally {
            reader.close();
        }
        Log.d(TAG, "Finished loading mArticleList.");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cuisine, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new GridLayoutManager(getActivity(),3);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new CuisineAdapter(mCuisineList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
