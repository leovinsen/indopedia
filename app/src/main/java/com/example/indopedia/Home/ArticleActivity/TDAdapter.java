package com.example.indopedia.Home.ArticleActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indopedia.R;

import java.util.ArrayList;

/**
 * Created by asus on 07/12/2017.
 */

//TTD stands for TouristDestination
public class TDAdapter extends RecyclerView.Adapter<TDAdapter.TTDViewHolder> {

    private ArrayList<TouristDestination> mTouristDestinationList;
    private FragmentManager mFragmentManager;

    TDAdapter(ArrayList<TouristDestination> dataset, FragmentManager fragmentManager) {
        this.mTouristDestinationList = dataset;
        this.mFragmentManager = fragmentManager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TDAdapter.TTDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_things_to_do_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        TDAdapter.TTDViewHolder vh = new TDAdapter.TTDViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TDAdapter.TTDViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.ttdPhoto.setImageResource(mTouristDestinationList.get(position).getPhotoId());
        holder.ttdTitle.setText(mTouristDestinationList.get(position).getTitle());
        holder.ttdDescription.setText(mTouristDestinationList.get(position).getDescription());

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTouristDestinationList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TTDViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        private ImageView ttdPhoto;
        private TextView ttdTitle;
        private TextView ttdDescription;
        //private ScrollView scrollView;


        public TTDViewHolder(View v) {
            super(v);
            ttdPhoto = v.findViewById(R.id.td_photo);
            ttdTitle = v.findViewById(R.id.td_title);
            ttdDescription = v.findViewById(R.id.td_content);

            //On clicking article, open new activity which contains more details
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment frag = mFragmentManager.findFragmentByTag("fragment_dialog");
                    if (frag != null) {
                        DestinationDialog dialog = (DestinationDialog) frag;
                        dialog.dismiss();
                    }

                    TouristDestination ttd = mTouristDestinationList.get(getAdapterPosition());
                    DestinationDialog dialog = DestinationDialog.newInstance(ttd.getPhotoId(), ttd.getTitle(), ttd.getDescription());
                    dialog.show(mFragmentManager, "fragment_dialog");
                }
            });
        }


    }
}
