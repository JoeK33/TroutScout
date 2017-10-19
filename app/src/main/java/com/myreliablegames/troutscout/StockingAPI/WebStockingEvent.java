package com.myreliablegames.troutscout.StockingAPI;

import com.myreliablegames.troutscout.StockingEvent;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

/**
 * Created by Joe on 10/14/2017.
 */

public class WebStockingEvent {
    @Selector(".dashed_border") public List<String> stockingData;

    public StockingEvent getStockingEvent() {
        ArrayList<String> data = new ArrayList<>();

        if (stockingData.size() == 7) {
            for (int i = 0; i < stockingData.size(); i++) {
                data.add(stockingData.get(i));
            }

            return new StockingEvent(data);
        }
        return null;
    }
}
