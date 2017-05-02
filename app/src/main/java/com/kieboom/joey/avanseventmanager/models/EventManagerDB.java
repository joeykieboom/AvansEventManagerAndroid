package com.kieboom.joey.avanseventmanager.models;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Joey on 6-4-2017.
 */

@Database(name = EventManagerDB.NAME, version = EventManagerDB.VERSION)
public class EventManagerDB {

    public static final String NAME = "EventManagerDB";

    public static final int VERSION = 1;
}
