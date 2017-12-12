package com.example.indopedia.Home.ArticleActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.indopedia.R;

/**
 * Created by asus on 12/12/2017.
 */

public class DestinationDialog extends DialogFragment {

    private ImageView mImageView;
    private TextView mTitle;
    private TextView mContent;

    public static DestinationDialog newInstance(int imageId, String title, String content) {
        DestinationDialog dialog = new DestinationDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("imageId", imageId);
        args.putString("title", title);
        args.putString("content", content);
        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View mView = inflater.inflate(R.layout.layout_destination_dialog, null);

        mImageView = mView.findViewById(R.id.dialog_image);
        mTitle = mView.findViewById(R.id.dialog_title);
        mContent = mView.findViewById(R.id.dialog_content);

        Bundle args = getArguments();
        mImageView.setImageResource(args.getInt("imageId"));
        mTitle.setText(args.getString("title"));
        mContent.setText(args.getString("content"));

        builder.setView(mView);

        return builder.create();
    }
}
