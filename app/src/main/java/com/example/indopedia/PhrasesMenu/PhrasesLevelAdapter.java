package com.example.indopedia.PhrasesMenu;

import android.content.Context;
import android.content.Intent;
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

//TTD stands for ThingsToDo
public class PhrasesLevelAdapter extends RecyclerView.Adapter<PhrasesLevelAdapter.PhrasesLevelViewHolder> {

    public static final String LEVEL_TITLE = "com.example.indopedia.TITLE";
    private ArrayList<Levels> levels;

    PhrasesLevelAdapter(ArrayList<Levels> levels) {
        this.levels = levels;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhrasesLevelAdapter.PhrasesLevelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_phrases_level_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PhrasesLevelAdapter.PhrasesLevelViewHolder vh = new PhrasesLevelAdapter.PhrasesLevelViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PhrasesLevelViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.levelImage.setImageResource(levels.get(position).getImageID());
        holder.levelTitle.setText(levels.get(position).getLevels());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return levels.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class PhrasesLevelViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case


        private ImageView levelImage;
        private TextView levelTitle;

        public PhrasesLevelViewHolder(View v) {
            super(v);
            levelImage = v.findViewById(R.id.levelImg);
            levelTitle = v.findViewById(R.id.levels);

            //On clicking article, open new activity which contains more details
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PhrasesActivity.class);
                    Levels level = levels.get(getAdapterPosition());
                    intent.putExtra(LEVEL_TITLE, level.getLevels());
                    context.startActivity(intent);
                }
            });
        }


    }
}
