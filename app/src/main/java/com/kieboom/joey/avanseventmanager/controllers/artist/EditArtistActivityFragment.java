package com.kieboom.joey.avanseventmanager.controllers.artist;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Artist;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.kieboom.joey.avanseventmanager.models.Performance_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditArtistActivityFragment extends Fragment {

    @BindView(R.id.edit_artist_artist_spinner) Spinner artistSpinner;
    @BindView(R.id.edit_artist_name) EditText artistNameTxt;
    @BindView(R.id.edit_artist_description) EditText artistDescriptionTxt;
    @BindView(R.id.edit_artist_btn) Button editArtistBtn;
    @BindView(R.id.delete_artist_btn) Button deleteArtistBtn;
    @BindView(R.id.edit_artist_info_frame) LinearLayout artistInfo;

    protected List<Artist> artists;

    public EditArtistActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_artist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupArtistSpinner();

        artistSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (artists.size() == 0)
                    return;

                artistInfo.setVisibility(View.VISIBLE);

                Artist artist = artists.get(artistSpinner.getSelectedItemPosition());
                artistNameTxt.setText(artist.artistName);
                artistDescriptionTxt.setText(artist.artistDescription);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                artistInfo.setVisibility(View.GONE);
            }
        });

        editArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Artist artist = artists.get(artistSpinner.getSelectedItemPosition());
                artist.artistName = artistNameTxt.getText().toString().trim();
                artist.artistDescription = artistDescriptionTxt.getText().toString().trim();
                artist.save();

                getActivity().finish();
            }
        });

        deleteArtistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Artist artist = artists.get(artistSpinner.getSelectedItemPosition());

                Performance performance = SQLite.select()
                        .from(Performance.class)
                        .where(Performance_Table.artist_id.eq(artist.id))
                        .querySingle();

                if (performance != null) {
                    performance.artist.delete();
                    performance.delete();
                }

                getActivity().finish();
            }
        });
    }

    private void setupArtistSpinner() {

        artists = SQLite.select().from(Artist.class).queryList();
        List<String> artistNames = new ArrayList<>();

        if (artists.size() > 0) {
            for (Artist artist : artists) {
                artistNames.add(artist.artistName);
            }
        } else {
            artistNames.add("You dont have any artists!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, artistNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        artistSpinner.setAdapter(adapter);
    }


}
