package com.myreliablegames.troutscout.API;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Joe on 10/14/2017.
 */

public interface StockDataAPI {

    String METHOD = "search.php?orderby=StockDate";

    @GET(METHOD)
    Observable<AllWebStockingEvents> getStockingData();
}
