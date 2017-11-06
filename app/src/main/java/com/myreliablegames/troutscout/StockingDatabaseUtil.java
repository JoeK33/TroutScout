package com.myreliablegames.troutscout;

import com.orm.query.Select;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Joe on 10/15/2017.
 */

public class StockingDatabaseUtil implements Serializable {

    public static final String KEY = "StockingDatabase";

    private static StockingDatabaseUtil instance;

    private StockingDatabaseUtil() {
    }

    public static StockingDatabaseUtil getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new StockingDatabaseUtil();
        return instance;
    }

    public Observable<List<LakeStockingHistory>> getLakeHistories() {
        final LakeStockingHistoryFactory lakeStockingHistoryFactory = new LakeStockingHistoryFactory();

        Observable<List<StockingEvent>> observable = Observable.create(new ObservableOnSubscribe<List<StockingEvent>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<StockingEvent>> e) throws Exception {
                Logger.e("Subscribed to stocking events");
                List<StockingEvent> allEvents = Select.from(StockingEvent.class).list();
                Logger.e("Database entries: " + String.valueOf(allEvents.size()));
                e.onNext(allEvents);
                e.onComplete();
            }
        });

        lakeStockingHistoryFactory.acceptStockingEvents(observable);
        return lakeStockingHistoryFactory.getLakeHistories();
    }

    public Observable<List<LakeStockingHistory>> getFavoriteLakes() {
        final Observable<List<LakeStockingHistory>> favoriteLakes = Observable.create(new ObservableOnSubscribe<List<LakeStockingHistory>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<LakeStockingHistory>> emitter) throws Exception {
                final List<FavoritedLake> favorites = FavoritesUtil.getFavoriteLakes();
                final List<LakeStockingHistory> favoriteLakes = new ArrayList<>();
                final List<LakeStockingHistory> allLakes = new ArrayList<>();

                getLakeHistories().subscribeWith(new DisposableObserver<List<LakeStockingHistory>>() {
                    @Override
                    public void onNext(@NonNull List<LakeStockingHistory> lakeStockingHistories) {
                        allLakes.clear();
                        allLakes.addAll(lakeStockingHistories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        for (FavoritedLake favoritedLake : favorites) {
                            for (LakeStockingHistory lakeStockingHistory : allLakes) {
                                if (favoritedLake.getName().equals(lakeStockingHistory.getLakeName()) && favoritedLake.getCounty().equals(lakeStockingHistory.getCounty())) {
                                    favoriteLakes.add(lakeStockingHistory);
                                }
                            }
                        }
                        emitter.onNext(favoriteLakes);
                        emitter.onComplete();
                    }
                });
            }
        });

        return favoriteLakes;
    }

    public Observable<List<StockingEvent>> getRecentLakeStockings() {
        Logger.i("Subscribed to recent stocking events");
        final Observable<List<StockingEvent>> recentLakeStockings = Observable.create(new ObservableOnSubscribe<List<StockingEvent>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<StockingEvent>> e) throws Exception {
                Observable.create(new ObservableOnSubscribe<List<StockingEvent>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<StockingEvent>> e) throws Exception {
                        List<StockingEvent> allEvents = Select.from(StockingEvent.class).list();
                        e.onNext(allEvents);
                        e.onComplete();
                    }
                }).subscribeWith(new DisposableObserver<List<StockingEvent>>() {
                    @Override
                    public void onNext(@NonNull List<StockingEvent> stockingEvents) {

                        Collections.sort(stockingEvents, new Comparator<StockingEvent>() {
                            @Override
                            public int compare(StockingEvent o1, StockingEvent o2) {
                                // Sort stocking events based on date to get most recent events.
                                DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, yyyy");
                                LocalDate stockDate1 = fmt.parseLocalDate(o1.getStockDate());
                                LocalDate stockDate2 = fmt.parseLocalDate(o2.getStockDate());

                                return stockDate2.compareTo(stockDate1);
                            }
                        });

                        // Only grab 20 most recent stocking events.
                        e.onNext(stockingEvents.subList(0, 20));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        e.onComplete();
                        Logger.i("Recent stocking events complete");
                    }
                });
            }
        });
        return recentLakeStockings;
    }

    /**
     * @return A list of CountyWrappers representing a county/zone and all the lakes stocked within it.
     */
    public Observable<List<CountyWrapper>> getCounties() {
        final Observable<List<CountyWrapper>> observable = Observable.create(new ObservableOnSubscribe<List<CountyWrapper>>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<List<CountyWrapper>> emitter) throws Exception {
                Logger.i("Subscribed to counties");
                getLakeHistories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<LakeStockingHistory>>() {
                    @Override
                    public void onNext(final @NonNull List<LakeStockingHistory> lakeStockingHistories) {
                        List<LakeStockingHistory> lakeStockingHistories1 = new ArrayList<LakeStockingHistory>(lakeStockingHistories);
                        final List<CountyWrapper> counties = new ArrayList<>();
                        final HashMap<String, List<LakeStockingHistory>> countiesMap = new HashMap<>();

                        for (LakeStockingHistory history : lakeStockingHistories1) {
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
                            Collections.sort(lakesList);
                            counties.add(new CountyWrapper(lakesList.get(0).getCounty(), lakesList));
                        }

                        Collections.sort(counties);
                        Logger.i("County Next");
                        emitter.onNext(counties);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("Counties complete");
                        emitter.onComplete();
                    }
                });
            }
        });

        return observable;
    }
}
