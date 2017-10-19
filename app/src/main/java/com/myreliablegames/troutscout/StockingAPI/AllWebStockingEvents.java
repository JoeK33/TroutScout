package com.myreliablegames.troutscout.StockingAPI;

import java.util.List;

import pl.droidsonroids.jspoon.annotation.Selector;

/**
 * Created by Joe on 10/14/2017.
 */

public class AllWebStockingEvents {

    @Selector(".reading_text") private List<WebStockingEvent> webStockingEvents;

    public List<WebStockingEvent> getWebStockingEvents() {
        return webStockingEvents;
    }
}
