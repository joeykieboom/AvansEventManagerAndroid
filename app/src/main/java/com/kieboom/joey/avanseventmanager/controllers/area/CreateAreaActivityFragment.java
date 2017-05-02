package com.kieboom.joey.avanseventmanager.controllers.area;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kieboom.joey.avanseventmanager.R;
import com.kieboom.joey.avanseventmanager.models.Area;
import com.kieboom.joey.avanseventmanager.models.Artist;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAreaActivityFragment extends Fragment {

    @BindView(R.id.create_area_name) EditText areaName;
    @BindView(R.id.create_area_description) EditText areaDescription;
    @BindView(R.id.create_area_btn) Button createAreaBtn;

    public CreateAreaActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_area, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        createAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Area area = new Area();
                area.areaName = areaName.getText().toString().trim();
                area.areaDescription = areaDescription.getText().toString().trim();
                area.save();

                getActivity().finish();
            }
        });
    }
}
