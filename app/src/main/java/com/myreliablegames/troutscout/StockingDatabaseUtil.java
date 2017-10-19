package com.myreliablegames.troutscout;

import com.orm.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Joe on 10/15/2017.
 */

public class StockingDatabaseUtil implements Serializable{

    public static final String KEY = "StockingDatabase";

    private List<LakeStockingHistory> lakeHistories = new ArrayList<>();

    public StockingDatabaseUtil() {
        LakeStockingHistoryManager lakeStockingHistoryManager = new LakeStockingHistoryManager();
        List<StockingEvent> allEvents = Select.from(StockingEvent.class).list();
        lakeStockingHistoryManager.acceptStockingEvents(allEvents);
        lakeHistories = lakeStockingHistoryManager.getLakeHistories();
        Collections.sort(lakeHistories);
    }

    public List<LakeStockingHistory> getLakeHistories() {
        return lakeHistories;
    }
}
