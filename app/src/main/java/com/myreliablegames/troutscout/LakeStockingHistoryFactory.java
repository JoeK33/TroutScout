package com.myreliablegames.troutscout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;


/**
 * Created by Joe on 10/15/2017.
 */

public class LakeStockingHistoryFactory {

    private List<StockingEvent> stockingEvents = new ArrayList<>();
    private HashMap<Key, List<StockingEvent>> stockingsByLakeAndCounty = new HashMap<>();
    private Observable<List<StockingEvent>> events;

    public void acceptStockingEvents(final Observable<List<StockingEvent>> events) {
        this.events = events;

        events.subscribeWith(new DisposableObserver<List<StockingEvent>>() {
            @Override
            public void onNext(@NonNull List<StockingEvent> stockingEvents) {
                Logger.e("Next stocking event");
                for (StockingEvent event : stockingEvents) {
                    addStockingEvent(event);
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Logger.e("Complete stocking events");
            }
        });
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

    public Observable<List<LakeStockingHistory>> getLakeHistories() {
        final List<LakeStockingHistory> histories = new ArrayList<>();
        final Set<Key> lakes = stockingsByLakeAndCounty.keySet();

        return Observable.create(new ObservableOnSubscribe<List<LakeStockingHistory>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<LakeStockingHistory>> e) throws Exception {
                Logger.e("subscribed to lake histories");

                // Subscribe to the database call to kick this off when the things load.
                events.subscribeWith(new DisposableObserver<List<StockingEvent>>() {
                    @Override
                    public void onNext(@NonNull List<StockingEvent> stockingEvents) {
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        for (Key lake : lakes) {
                            LakeStockingHistory lakeStockingHistory = new LakeStockingHistory();
                            lakeStockingHistory.setLakeName(lake.getName());
                            lakeStockingHistory.setCounty(lake.getCounty());

                            List<LakeStockingEvent> lakeEvents = new ArrayList<>();
                            for (StockingEvent event : stockingsByLakeAndCounty.get(lake)) {
                                lakeEvents.add(new LakeStockingEvent(event));
                            }
                            lakeStockingHistory.setStockings(lakeEvents);
                            histories.add(lakeStockingHistory);
                            // sent whole list every 20 lakes as they are added
                            if (histories.size() % 20 == 0) {
                                e.onNext(histories);
                            }
                        }
                        e.onNext(histories);
                        Logger.e("complete events in lake histories");
                        e.onComplete();
                    }
                });
            }
        });
    }

    public Observable<List<StockingEvent>> getStockingEvents() {
        return Observable.create(new ObservableOnSubscribe<List<StockingEvent>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<StockingEvent>> e) throws Exception {
                Logger.e("subscribed to stocking events for database save");
                e.onNext(stockingEvents);
                e.onComplete();
            }
        });
    }

    private class Key {
        private String name;
        private String county;

        public Key(String name, String county) {
            this.name = name;
            this.county = county;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Key) {
                Key otherKey = (Key) o;
                if ((otherKey.getName().equals(this.name) && otherKey.getCounty().equals(this.county))) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
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
