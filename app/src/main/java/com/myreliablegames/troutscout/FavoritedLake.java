package com.myreliablegames.troutscout;

import com.orm.SugarRecord;

/**
 * Created by Joe on 11/5/2017.
 */

public class FavoritedLake extends SugarRecord<FavoritedLake> {

    private String name;
    private String county;

    public FavoritedLake() {
    }

    public FavoritedLake(String name, String county) {
        this.name = name;
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public String getCounty() {
        return county;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
