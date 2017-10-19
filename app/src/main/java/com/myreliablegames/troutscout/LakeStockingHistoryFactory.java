package com.myreliablegames.troutscout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


/**
 * Created by Joe on 10/15/2017.
 */

public class LakeStockingHistoryFactory {

    private List<StockingEvent> stockingEvents = new ArrayList<>();
    private HashMap<Key, List<StockingEvent>> stockingsByLakeAndCounty = new HashMap<>();

    public void acceptStockingEvents(List<StockingEvent> events) {
        for (StockingEvent event : events) {
            addStockingEvent(event);
        }
    }

    public void addStockingEvent(StockingEvent event) {
        if (event.getName() == null) {
            return;
        }
        stockingEvents.add(event);
        // Seperate stockings by lake and county
        Key key = new Key(event.getName(), event.getCounty());
        if (stockingsByLakeAndCounty.containsKey(key)) {
            // lake already exists, add this event to the list and put it in the map
            List<StockingEvent> events = stockingsByLakeAndCounty.get(key);
            events.add(event);
            stockingsByLakeAndCounty.put(key, events);
        } else {
            // new lake, make a new list of events and add this one.
            List<StockingEvent> events = new ArrayList<>();
            events.add(event);
            stockingsByLakeAndCounty.put(key, events);
        }
    }

    public List<LakeStockingHistory> getLakeHistories() {
        List<LakeStockingHistory> histories = new ArrayList<>();
        Set<Key> lakes = stockingsByLakeAndCounty.keySet();

        for(Key lake : lakes) {
            LakeStockingHistory lakeStockingHistory = new LakeStockingHistory();
            lakeStockingHistory.setLakeName(lake.getName());
            lakeStockingHistory.setCounty(lake.getCounty());

            List<LakeStockingEvent> lakeEvents = new ArrayList<>();
            for (StockingEvent event : stockingsByLakeAndCounty.get(lake)) {
                lakeEvents.add(new LakeStockingEvent(event));
            }
            lakeStockingHistory.setStockings(lakeEvents);
            histories.add(lakeStockingHistory);
        }

        return histories;
    }

    public List<StockingEvent> getStockingEvents() {
        return stockingEvents;
    }

    private class Key {
        private String name;
        private String county;

        public Key(String name, String county) {
            this.name = name;
            this.county = county;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o instanceof Key) {
                Key otherKey = (Key)o;
                if ((otherKey.getName().equals(this.name) && otherKey.getCounty().equals(this.county))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            return name.hashCode() + county.hashCode();
        }

        public String getName() {
            return name;
        }

        public String getCounty() {
            return county;
        }
    }
}
