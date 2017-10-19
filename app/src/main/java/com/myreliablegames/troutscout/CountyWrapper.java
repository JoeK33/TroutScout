package com.myreliablegames.troutscout;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 10/18/2017.
 */

public class CountyWrapper implements Comparable<CountyWrapper> {

    private List<LakeStockingHistory> lakes = new ArrayList<>();
    private String county;

    public CountyWrapper (String county, List<LakeStockingHistory> lakes) {
        this.county = county;
        this.lakes.addAll(lakes);
    }

    public String getCounty() {
        return this.county;
    }

    public List<LakeStockingHistory> getLakes() {
        return this.lakes;
    }

    public String getLakesCountString() {
        return Integer.toString(this.lakes.size());
    }

    public String getFirstLakeName() {
        return lakes.get(0).getLakeName();
    }

    @Override
    public int compareTo(@NonNull CountyWrapper o) {
        return county.compareTo(o.getCounty());
    }
}
