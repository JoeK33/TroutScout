package com.myreliablegames.troutscout;

import android.content.res.Resources;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.myreliablegames.troutscout.StockingAPI.AllWebStockingEvents;
import com.myreliablegames.troutscout.StockingAPI.StockDataAPI;
import com.myreliablegames.troutscout.StockingAPI.WebStockingEvent;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import pl.droidsonroids.retrofit2.JspoonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Joe on 10/15/2017.
 */

public class StockingEventTask {

    private LakeStockingHistoryFactory lakeStockingHistoryFactory = new LakeStockingHistoryFactory();

    private Observable<AllWebStockingEvents> data;

    public StockingEventTask(Resources resources) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(resources.getString(R.string.BASE_URL))
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        StockDataAPI stockDataAPI = retrofit.create(StockDataAPI.class);
        this.data = stockDataAPI.getStockingData();
    }

    public void run() {
        this.data.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getDisposableObserver());
    }

    private DisposableObserver<AllWebStockingEvents> getDisposableObserver() {
        DisposableObserver<AllWebStockingEvents> observer = new DisposableObserver<AllWebStockingEvents>() {
            @Override
            public void onNext(@NonNull AllWebStockingEvents stockings) {
                List<WebStockingEvent> webStockingEvents = stockings.getWebStockingEvents();
                for (WebStockingEvent event : webStockingEvents) {
                    if (event.getStockingEvent() != null) {
                        lakeStockingHistoryFactory.addStockingEvent(event.getStockingEvent());
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                saveDataToDatabase();
            }
        };
        return observer;
    }

    private void saveDataToDatabase() {
        lakeStockingHistoryFactory.getStockingEvents().subscribeWith(new DisposableObserver<List<StockingEvent>>() {
            @Override
            public void onNext(@NonNull List<StockingEvent> stockingEvents) {
                for (StockingEvent stockingEvent : stockingEvents) {
                    StockingEvent otherEvent = Select.from(StockingEvent.class).where(
                            Condition.prop("name").eq(stockingEvent.getName()),
                            Condition.prop("county").eq(stockingEvent.getCounty()),
                            Condition.prop("stock_date").eq(stockingEvent.getStockDate()),
                            Condition.prop("fish_species").eq(stockingEvent.getFishSpecies()),
                            Condition.prop("number_of_fish_stocked").eq(stockingEvent.getNumberOfFishStocked())).first();

                    // only save events that are not found.
                    if (otherEvent == null) {
                        stockingEvent.save();
                    }
                }
                Logger.i("Database count", Long.toString(StockingEvent.count(StockingEvent.class, null, null)));
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
