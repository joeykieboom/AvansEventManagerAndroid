package com.kieboom.joey.avanseventmanager.controllers.performance;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Area;
import com.kieboom.joey.avanseventmanager.models.Artist;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatePerformanceActivityFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.create_performance_artist_spinner) Spinner artistSpinner;
    @BindView(R.id.create_performance_area_spinner) Spinner areaSpinner;

    @BindView(R.id.create_performance_date) EditText dateTxt;
    @BindView(R.id.create_performance_start_time) EditText startTimeTxt;
    @BindView(R.id.create_performance_end_time) EditText endTimeTxt;

    @BindView(R.id.create_performance_btn) Button createPerformanceBtn;

    private DateTime startTime;
    private DateTime endTime;
    private List<Artist> artists;
    private List<Area> areas;

    public CreatePerformanceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_performance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupArtistSpinner();
        setupAreaSpinner();

        dateTxt.setOnClickListener(this);
        startTimeTxt.setOnClickListener(this);
        endTimeTxt.setOnClickListener(this);
        createPerformanceBtn.setOnClickListener(this);

        startTime = new DateTime();
        endTime = new DateTime();
    }

    private void setupAreaSpinner() {

        areas = SQLite.select().from(Area.class).queryList();
        List<String> areaNames = new ArrayList<>();

        if (areas.size() > 0) {
            for (Area area : areas) {
                areaNames.add(area.areaName);
            }
        } else {
            areaNames.add("You dont have any area!");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, areaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        areaSpinner.setAdapter(adapter);
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.create_performance_date:

                DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        startTime.withYear(i);
                        startTime.withMonthOfYear(i1);
                        startTime.withDayOfMonth(i2);

                        endTime.withYear(i);
                        endTime.withMonthOfYear(i1);
                        endTime.withDayOfMonth(i2);

                        dateTxt.setText(i2 + "/" + i1 + "/" + i);
                    }
                }, 2017, 1, 1);

                datePicker.show();

                break;
            case R.id.create_performance_start_time:

                TimePickerDialog startTimeDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        startTimeTxt.setText(i + ":" + i1);
                        startTime.withHourOfDay(i);
                        startTime.withMinuteOfHour(i1);
                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                startTimeDialog.show();

                break;
            case R.id.create_performance_end_time:

                TimePickerDialog endTimeDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {

                        endTimeTxt.setText(i + ":" + i1);
                        endTime.withHourOfDay(i);
                        endTime.withMinuteOfHour(i1);

                    }
                }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, true);

                endTimeDialog.show();

                break;
            case R.id.create_performance_btn:

                Performance performance = new Performance();
                performance.artist = artists.get(artistSpinner.getSelectedItemPosition());
                performance.area = areas.get(areaSpinner.getSelectedItemPosition());
                performance.startTime = startTime.getMillis();
                performance.endTime = endTime.getMillis();

                performance.save();
                getActivity().finish();

                break;
            default:

                break;
        }
    }
}
