package com.kieboom.joey.avanseventmanager.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.controllers.area.CreateAreaActivity;
import com.kieboom.joey.avanseventmanager.controllers.area.EditAreaActivity;
import com.kieboom.joey.avanseventmanager.controllers.artist.CreateArtistActivity;
import com.kieboom.joey.avanseventmanager.controllers.artist.EditArtistActivity;
import com.kieboom.joey.avanseventmanager.controllers.performance.CreatePerformanceActivity;
import com.kieboom.joey.avanseventmanager.controllers.performance.EditPerformanceActivity;
import com.kieboom.joey.avanseventmanager.controllers.performance.PerformanceOverviewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.main_create_artist) Button createArtistBtn;
    @BindView(R.id.main_create_area) Button createAreaBtn;
    @BindView(R.id.main_create_performance) Button createPerformanceBtn;
    @BindView(R.id.main_edit_artist) Button editArtistBtn;
    @BindView(R.id.main_edit_area) Button editAreaBtn;
    @BindView(R.id.main_edit_performance) Button editPerformanceBtn;
    @BindView(R.id.main_performance_overview) Button performanceOverviewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        createArtistBtn.setOnClickListener(this);
        createAreaBtn.setOnClickListener(this);
        createPerformanceBtn.setOnClickListener(this);
        editArtistBtn.setOnClickListener(this);
        editAreaBtn.setOnClickListener(this);
        editPerformanceBtn.setOnClickListener(this);
        performanceOverviewBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.main_create_artist:

                startActivity(new Intent(this, CreateArtistActivity.class));
                break;
            case R.id.main_create_area:

                startActivity(new Intent(this, CreateAreaActivity.class));
                break;
            case R.id.main_create_performance:

                startActivity(new Intent(this, CreatePerformanceActivity.class));
                break;
            case R.id.main_edit_artist:

                startActivity(new Intent(this, EditArtistActivity.class));
                break;
            case R.id.main_edit_area:

                startActivity(new Intent(this, EditAreaActivity.class));
                break;
            case R.id.main_edit_performance:

                startActivity(new Intent(this, EditPerformanceActivity.class));
                break;
            case R.id.main_performance_overview:

                startActivity(new Intent(this, PerformanceOverviewActivity.class));
                break;
            default:

                break;


        }
    }
}
