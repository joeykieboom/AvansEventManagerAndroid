package com.kieboom.joey.avanseventmanager.controllers.area;

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
import com.kieboom.joey.avanseventmanager.models.Area;
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
public class EditAreaActivityFragment extends Fragment {

    @BindView(R.id.edit_area_name) EditText areaName;
    @BindView(R.id.edit_area_description) EditText areaDescription;
    @BindView(R.id.edit_area_btn) Button editAreaBtn;
    @BindView(R.id.delete_area_btn) Button deleteAreaBtn;
    @BindView(R.id.edit_area_info_frame) LinearLayout areaInfo;
    @BindView(R.id.edit_area_area_spinner) Spinner areaSpinner;

    protected List<Area> areas;

    public EditAreaActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_area, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        setupAreaSpinner();

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (areas.size() == 0)
                    return;

                areaInfo.setVisibility(View.VISIBLE);

                Area area = areas.get(areaSpinner.getSelectedItemPosition());
                areaName.setText(area.areaName);
                areaDescription.setText(area.areaDescription);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                areaInfo.setVisibility(View.GONE);
            }
        });

        editAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Area area = areas.get(areaSpinner.getSelectedItemPosition());
                area.areaName = areaName.getText().toString().trim();
                area.areaDescription = areaDescription.getText().toString().trim();
                area.save();

                getActivity().finish();
            }
        });

        deleteAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Area area = areas.get(areaSpinner.getSelectedItemPosition());

                Performance performance = SQLite.select()
                        .from(Performance.class)
                        .where(Performance_Table.area_id.eq(area.id))
                        .querySingle();

                if (performance != null) {
                    performance.area.delete();
                    performance.delete();
                }

                area.delete();

                getActivity().finish();
            }
        });
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
}
