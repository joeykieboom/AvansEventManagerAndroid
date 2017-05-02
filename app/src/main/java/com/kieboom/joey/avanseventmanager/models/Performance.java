package com.kieboom.joey.avanseventmanager.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Joey on 6-4-2017.
 */
@Table(database = EventManagerDB.class)
public class Performance extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public Long id;

    @Column
    public Long startTime;

    @Column
    public Long endTime;

    @ForeignKey
    public Artist artist;

    @ForeignKey
    public Area area;
}
