package com.kieboom.joey.avanseventmanager;

import android.app.Application;

import com.kieboom.joey.avanseventmanager.models.Area;
import com.kieboom.joey.avanseventmanager.models.Artist;
import com.kieboom.joey.avanseventmanager.models.Performance;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Joey on 6-4-2017.
 */

public class AvansEventManagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(new FlowConfig.Builder(this).build());

        Artist artist1 = new Artist();
        artist1.artistName = "Joey";
        artist1.artistDescription = "Joey's description";
        artist1.save();
        Artist artist2 = new Artist();
        artist2.artistName = "Okke";
        artist2.artistDescription = "Okke's description";
        artist2.save();
        Artist artist3 = new Artist();
        artist3.artistName = "Thijmen";
        artist3.artistDescription = "Thijmen's description";
        artist3.save();

        Area area1 = new Area();
        area1.areaName = "Area 1";
        area1.areaDescription = "Area 1 description";
        area1.save();
        Area area2 = new Area();
        area2.areaName = "Area 2";
        area2.areaDescription = "Area 2 description";
        area2.save();
        Area area3 = new Area();
        area3.areaName = "Area 3";
        area3.areaDescription = "Area 3 description";
        area3.save();

        Performance performance1 = new Performance();
        performance1.startTime = Long.valueOf(1491560044);
        performance1.endTime = Long.valueOf(1491570044);
        performance1.artist = artist1;
        performance1.area = area1;
        performance1.save();

        Performance performance2 = new Performance();
        performance2.startTime = Long.valueOf(1491560044);
        performance2.endTime = Long.valueOf(1491570044);
        performance2.artist = artist2;
        performance2.area = area2;
        performance2.save();

        Performance performance3 = new Performance();
        performance3.startTime = Long.valueOf(1491560044);
        performance3.endTime = Long.valueOf(1491570044);
        performance3.artist = artist3;
        performance3.area = area3;
        performance3.save();

    }
}
