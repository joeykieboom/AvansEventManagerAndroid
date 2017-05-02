package com.kieboom.joey.avanseventmanager.controllers.performance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Performance;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerformanceFragment extends Fragment {


    @BindView(R.id.performance_artist_name) TextView artistName;
    @BindView(R.id.performance_artist_description) TextView artistDescription;
    @BindView(R.id.performance_area_name) TextView areaName;
    @BindView(R.id.performance_area_description) TextView areaDescription;
    @BindView(R.id.performance_date_description) TextView dateDescription;

    protected Performance performance;

    public PerformanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_performance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupViews();
    }

    private void setupViews() {

        if (performance.area != null) {
            areaName.setText(performance.area.areaName);
            areaDescription.setText(performance.area.areaDescription);
        }
        if (performance.artist != null) {
            artistName.setText(performance.artist.artistName);
            artistDescription.setText(performance.artist.artistDescription);
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        String date = fmt.print(performance.startTime);
        dateDescription.setText(date);
    }

    public void addPerformance(Performance performance) {

        this.performance = performance;
        Log.i("asd", "addPerformance: " + performance.id);
    }
}
