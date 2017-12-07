package com.example.indopedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by asus on 06/12/2017.
 */

public class ArticleActivity extends AppCompatActivity {

    private ImageView mArticleHeader;
    private TextView mArticleText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        mArticleHeader = findViewById(R.id.article_header);
        mArticleText = findViewById(R.id.article_text);

        mArticleHeader.setImageResource(extras.getInt(HomeAdapter.ARTICLE_HEADER));
        mArticleText.setText(extras.getString(HomeAdapter.ARTICLE_TEXT));

    }


}
