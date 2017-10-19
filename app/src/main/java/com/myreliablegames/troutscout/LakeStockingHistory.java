package com.myreliablegames.troutscout;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeStockingHistory implements Comparable<LakeStockingHistory>, Serializable {

    private String lakeName;
    private String county;
    private List<LakeStockingEvent> stockings = new ArrayList<>();

    public LakeStockingHistory() {

    }

    public String getLakeName() {
        return lakeName;
    }

    public void setLakeName(String lakeName) {
        this.lakeName = lakeName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public List<LakeStockingEvent> getStockings() {
        return stockings;
    }

    public void setStockings(List<LakeStockingEvent> stockings) {
        this.stockings = stockings;
        Collections.sort(this.stockings);
    }

    @Override
    public int compareTo(@NonNull LakeStockingHistory o) {
        return getLakeName().compareTo(o.getLakeName());
    }
}
