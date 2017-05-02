package com.kieboom.joey.avanseventmanager.controllers.artist;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Artist;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateArtistActivityFragment extends Fragment {

    @BindView(R.id.create_artist_name) EditText artistName;
    @BindView(R.id.create_artist_description) EditText artistDescription;
    @BindView(R.id.create_artist_btn) Button createArtistBtn;

    public CreateArtistActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_artist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        createArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Artist artist = new Artist();
                artist.artistName = artistName.getText().toString().trim();
                artist.artistDescription = artistDescription.getText().toString().trim();
                artist.save();

                getActivity().finish();
            }
        });
    }
}
