package com.example.movieapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.movieapp.R;
import com.squareup.picasso.Picasso;


import static com.example.movieapp.ui.HomeFragment.IMAGE_URL_KEY;
import static com.example.movieapp.ui.HomeFragment.STORY_KEY;
import static com.example.movieapp.ui.HomeFragment.TITLE_KEY;

public class DetailedFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);
        ImageView headerImage = view.findViewById(R.id.detailed_header_icon);
        ImageView smallIcon = view.findViewById(R.id.detailed_small_icon);
        TextView titleTextView = view.findViewById(R.id.detailed_title);
        TextView storyTextView = view.findViewById(R.id.detailed_story);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String imageURL = bundle.getString(IMAGE_URL_KEY);
            String title = bundle.getString(TITLE_KEY);
            String story = bundle.getString(STORY_KEY);

            titleTextView.setText(title);
            storyTextView.setText(story);

            Picasso.get().load(imageURL).into(smallIcon);
            Picasso.get().load(imageURL).into(headerImage);
        }
        return view;
    }
}
