package com.myreliablegames.troutscout;

import android.content.Context;
import android.content.SharedPreferences;

import com.orm.SugarApp;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Joe on 10/14/2017.
 */

public class TroutScoutApp extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        updateStockingDataIfNeeded();
    }

  //  private static final long TWENTY_FOUR_HOURS_MILLI = 86400000;
    //TODO turn back on update timer
    private static final long TWENTY_FOUR_HOURS_MILLI = 0;

    private void updateStockingDataIfNeeded() {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        long lastUpdatedTime = preferences.getLong("lastUpdatedTime", 0);

        // Only perform the update call once every 24 hours.
        if (lastUpdatedTime + TWENTY_FOUR_HOURS_MILLI < System.currentTimeMillis()) {
            Logger.i("Updating database");
            // Update the database
            SharedPreferences.Editor editor = preferences.edit();
            editor.putLong("lastUpdatedTime", System.currentTimeMillis());
            editor.apply();

            StockingEventTask stockingEventTask = new StockingEventTask(getResources());
            stockingEventTask.run();
        }
    }
}
