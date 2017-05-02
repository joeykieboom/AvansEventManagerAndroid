package com.kieboom.joey.avanseventmanager.controllers.performance;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Area;
import com.kieboom.joey.avanseventmanager.models.Artist;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.kieboom.joey.avanseventmanager.models.Performance_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditPerformanceActivityFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.edit_performance_info_frame) LinearLayout performanceInfoFrame;

    @BindView(R.id.edit_performance_area_spinner) Spinner areaSpinner;
    @BindView(R.id.edit_performance_artist_spinner) Spinner artistSpinner;
    @BindView(R.id.edit_performance_performance_spinner) Spinner performanceSpinner;

    @BindView(R.id.edit_performance_date) EditText dateTxt;
    @BindView(R.id.edit_performance_start_time) EditText startTimeTxt;
    @BindView(R.id.edit_performance_end_time) EditText endTimeTxt;

    @BindView(R.id.edit_performance_btn) Button editPerformanceBtn;
    @BindView(R.id.delete_performance_btn) Button deletePerformanceBtn;

    private DateTime startTime;
    private DateTime endTime;
    private List<Artist> artists;
    private List<Area> areas;
    private List<Performance> performances;

    public EditPerformanceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_performance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        dateTxt.setOnClickListener(this);
        startTimeTxt.setOnClickListener(this);
        endTimeTxt.setOnClickListener(this);
        editPerformanceBtn.setOnClickListener(this);
        deletePerformanceBtn.setOnClickListener(this);

        startTime = new DateTime();
        endTime = new DateTime();

        setupPerformanceSpinner();

        performanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (performances.size() == 0)
                    return;

                Performance performance = performances.get(performanceSpinner.getSelectedItemPosition());

                performanceInfoFrame.setVisibility(View.VISIBLE);
                if (performance.area != null) {
                    setupAreaSpinner(performance.area.id);
                }

                if (performance.artist != null) {
                    setupArtistSpinner(performance.artist.id);
                }

                setupDates(performance.startTime, performance.endTime);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                performanceInfoFrame.setVisibility(View.GONE);
            }
        });
    }

    private void setupDates(Long startTime, Long endTime) {

        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        DateTimeFormatter fmtTime = DateTimeFormat.forPattern("HH:mm");
        DateTime startDate = new DateTime(startTime);
        DateTime endDate = new DateTime(endTime);

        dateTxt.setText(fmt.print(startDate));
        startTimeTxt.setText(fmtTime.print(startDate));
        endTimeTxt.setText(fmtTime.print(endDate));
    }

    private void setupPerformanceSpinner() {

        performances = SQLite.select().from(Performance.class).queryList();
        List<String> performanceNames = new ArrayList<>();

        if (performances.size() > 0) {
            for (Performance performance : performances) {
                performanceNames.add(performance.id.toString());
            }
        } else {
            performanceNames.add("You dont have any performance!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, performanceNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        performanceSpinner.setAdapter(adapter);
    }

    private void setupAreaSpinner(long id) {

        areas = SQLite.select().from(Area.class).queryList();
        List<String> areaNames = new ArrayList<>();

        int indexOfPerformanceArea = 0;

        if (areas.size() > 0) {
            for (Area area : areas) {
                areaNames.add(area.areaName);

                if (area.id.equals(id))
                    indexOfPerformanceArea = areaNames.size() - 1;
            }
        } else {
            areaNames.add("You dont have any area!");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, areaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        areaSpinner.setAdapter(adapter);

        areaSpinner.setSelection(indexOfPerformanceArea);
    }

    private void setupArtistSpinner(long id) {

        artists = SQLite.select().from(Artist.class).queryList();
        List<String> artistNames = new ArrayList<>();

        int indexOfPerformanceArtist = 0;

        if (artists.size() > 0) {
            for (Artist artist : artists) {
                artistNames.add(artist.artistName);

                if (artist.id.equals(id))
                    indexOfPerformanceArtist = artistNames.size() - 1;
            }
        } else {
            artistNames.add("You dont have any artists!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, artistNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        artistSpinner.setAdapter(adapter);

        artistSpinner.setSelection(indexOfPerformanceArtist);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.edit_performance_date:

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
            case R.id.edit_performance_start_time:

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
            case R.id.edit_performance_end_time:

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
            case R.id.edit_performance_btn:

                Performance performance = SQLite
                        .select()
                        .from(Performance.class)
                        .where(Performance_Table.id.eq(performances.get(performanceSpinner.getSelectedItemPosition()).id))
                        .querySingle();

                performance.artist = artists.get(artistSpinner.getSelectedItemPosition());
                performance.area = areas.get(areaSpinner.getSelectedItemPosition());
                performance.startTime = startTime.getMillis();
                performance.endTime = endTime.getMillis();

                performance.save();
                getActivity().finish();

                break;
            case R.id.delete_performance_btn:

                Performance performanceDel = SQLite
                        .select()
                        .from(Performance.class)
                        .where(Performance_Table.id.eq(performances.get(performanceSpinner.getSelectedItemPosition()).id))
                        .querySingle();

                performanceDel.delete();
                getActivity().finish();

                break;
            default:

                break;
        }
    }
}
