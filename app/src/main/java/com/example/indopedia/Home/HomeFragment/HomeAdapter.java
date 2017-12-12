package com.example.indopedia.Home.HomeFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indopedia.Home.ArticleActivity.ArticleActivity;
import com.example.indopedia.R;

import java.util.ArrayList;

/**
 * Created by asus on 18/09/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ArticleViewHolder> {
    public static final String ARTICLE_TITLE = "com.example.indopedia.Home.HomeFragment.TITLE";
    public static final String ARTICLE_HEADER = "com.example.indopedia.Home.HomeFragment.HEADER";
    public static final String ARTICLE_TEXT = "com.example.indopedia.Home.HomeFragment.TEXT";
    private ArrayList<Article> mArticleList;

    HomeAdapter(ArrayList<Article> mArticleList){
        this.mArticleList = mArticleList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_home_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ArticleViewHolder vh = new ArticleViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.articlePhoto.setImageResource(mArticleList.get(position).getPhotoId());
        holder.articleTitle.setText(mArticleList.get(position).getTitle());
        //holder.articleText.setText(mArticleList.get(position).getContent());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        CardView cv;
        ImageView articlePhoto;
        TextView articleTitle;
        //TextView articleText;

        public ArticleViewHolder(View v) {
            super(v);
            cv = v.findViewById(R.id.cardview);
            articlePhoto = v.findViewById(R.id.article_photo);
            articleTitle = v.findViewById(R.id.article_title);
            //articleText = v.findViewById(R.id.article_text);

            //On clicking article, open new activity which contains more details
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ArticleActivity.class);
                    Article article = mArticleList.get(getAdapterPosition());
                    intent.putExtra(ARTICLE_TITLE, article.getTitle());
                    intent.putExtra(ARTICLE_HEADER, article.getPhotoId());
                    intent.putExtra(ARTICLE_TEXT, article.getContent());
                    context.startActivity(intent);
                }
            });
        }


    }



}
