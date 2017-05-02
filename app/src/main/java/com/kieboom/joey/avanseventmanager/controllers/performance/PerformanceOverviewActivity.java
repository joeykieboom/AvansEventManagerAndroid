package com.kieboom.joey.avanseventmanager.controllers.performance;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

public class PerformanceOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_overview);

        List<Performance> performances = SQLite.select().from(Performance.class).queryList();
        Log.i("sad", "onCreate: ");
    }

}
