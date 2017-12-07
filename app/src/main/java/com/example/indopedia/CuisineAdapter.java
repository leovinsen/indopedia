package com.example.indopedia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by asus on 06/12/2017.
 */

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>{
    
    private ArrayList<Cuisine> mCuisineList;

    CuisineAdapter(ArrayList<Cuisine> mCuisineList){
        this.mCuisineList = mCuisineList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CuisineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cuisine_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CuisineViewHolder vh = new CuisineViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CuisineViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.cuisineImage.setImageResource(mCuisineList.get(position).getPhotoId());
        holder.cuisineName.setText(mCuisineList.get(position).getName());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mCuisineList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class CuisineViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private ImageView cuisineImage;
        private TextView cuisineName;

        public CuisineViewHolder(View v) {
            super(v);
            cuisineImage = v.findViewById(R.id.cuisine_image);
            cuisineName = v.findViewById(R.id.cuisine_name);

            //On clicking article, open new activity which contains more details
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    //Intent intent = new Intent(context, ArticleActivity.class);

                    //context.startActivity(intent);
                }
            });
        }


    }
}
