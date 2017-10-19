package com.myreliablegames.troutscout;

import com.orm.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joe on 10/15/2017.
 */

public class StockingDatabaseUtil implements Serializable{

    public static final String KEY = "StockingDatabase";

    private List<LakeStockingHistory> lakeHistories = new ArrayList<>();

    public StockingDatabaseUtil() {
        LakeStockingHistoryFactory lakeStockingHistoryFactory = new LakeStockingHistoryFactory();
        List<StockingEvent> allEvents = Select.from(StockingEvent.class).list();
        lakeStockingHistoryFactory.acceptStockingEvents(allEvents);
        lakeHistories = lakeStockingHistoryFactory.getLakeHistories();
        Collections.sort(lakeHistories);
    }

    public List<LakeStockingHistory> getLakeHistories() {
        return lakeHistories;
    }

    /**
     *
     * @return A list of CountyWrappers representing a county/zone and all the lakes stocked within it.
     */
    public List<CountyWrapper> getCounties() {
        List<CountyWrapper> counties = new ArrayList<>();

        HashMap<String, List<LakeStockingHistory>> countiesMap = new HashMap<>();

        for (LakeStockingHistory history : lakeHistories) {
            if (countiesMap.containsKey(history.getCounty())) {
                countiesMap.get(history.getCounty()).add(history);
            } else {
                ArrayList<LakeStockingHistory> historyList = new ArrayList<>();
                historyList.add(history);
                countiesMap.put(history.getCounty(), historyList);
            }
        }

        Collection<List<LakeStockingHistory>> countyLists = countiesMap.values();

        for (List<LakeStockingHistory> lakesList : countyLists) {
            counties.add(new CountyWrapper(lakesList.get(0).getCounty(), lakesList));
        }

        Collections.sort(counties);
        return counties;
    }
}
