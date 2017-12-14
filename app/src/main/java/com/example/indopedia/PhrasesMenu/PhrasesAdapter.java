package com.example.indopedia.PhrasesMenu;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.indopedia.R;

import java.util.ArrayList;

/**
 * Created by Velcone on 12/5/2017.
 */

public class PhrasesAdapter extends RecyclerView.Adapter<PhrasesAdapter.PhrasesViewHolder> {
    private ArrayList<Phrases> phrases;
    private MediaPlayer mediaPlayer;

    PhrasesAdapter(ArrayList<Phrases> phrases) {
        this.phrases = phrases;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhrasesAdapter.PhrasesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.phrases_card_view, parent, false);
        mediaPlayer = new MediaPlayer();
        // set the view's size, margins, paddings and layout parameters
        PhrasesAdapter.PhrasesViewHolder vh = new PhrasesAdapter.PhrasesViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(PhrasesAdapter.PhrasesViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.indoWords.setText(phrases.get(position).getIndoWords());
        holder.engWords.setText(phrases.get(position).getEngWords());
        //holder.audioID = phrases.get(position).getAudioID();

        holder.audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.findViewById(R.id.audioButton).getContext();
                //MediaPlayer mediaPlayer = new MediaPlayer();
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                try {
                    mediaPlayer.reset();
                    mediaPlayer = MediaPlayer.create(context, phrases.get(position).getAudioID());
                    mediaPlayer.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return phrases.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class PhrasesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView indoWords;
        TextView engWords;
        ImageButton audioButton;

        public PhrasesViewHolder(View v) {
            super(v);
            cv = v.findViewById(R.id.phrases_card_view);
            indoWords = v.findViewById(R.id.indoWords);
            engWords = v.findViewById(R.id.engWords);
            audioButton = v.findViewById(R.id.audioButton);
        }
    }


}
