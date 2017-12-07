package com.example.indopedia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asus on 07/12/2017.
 */

//TTD stands for ThingsToDo
public class TTDAdapter extends RecyclerView.Adapter<TTDAdapter.TTDViewHolder> {

    private ArrayList<ThingsToDo> mThingsToDoList;

    TTDAdapter(ArrayList<ThingsToDo> mThingsToDoList){
        this.mThingsToDoList = mThingsToDoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TTDAdapter.TTDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_things_to_do_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TTDAdapter.TTDViewHolder vh = new TTDAdapter.TTDViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TTDAdapter.TTDViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.ttdPhoto.setImageResource(mThingsToDoList.get(position).getPhotoId());
        holder.ttdTitle.setText(mThingsToDoList.get(position).getTitle());
        holder.ttdDescription.setText(mThingsToDoList.get(position).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mThingsToDoList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TTDViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private ImageView ttdPhoto;
        private TextView ttdTitle;
        private TextView ttdDescription;

        public TTDViewHolder(View v) {
            super(v);
            ttdPhoto = v.findViewById(R.id.ttd_photo);
            ttdTitle = v.findViewById(R.id.ttd_title);
            ttdDescription = v.findViewById(R.id.ttd_description);

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
