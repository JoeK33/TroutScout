package com.myreliablegames.troutscout.API;

import com.myreliablegames.troutscout.API.WeatherForecastModel.RawWeatherForecast;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Joe on 11/11/2017.
 */

public interface WeatherForecastAPI {
    String METHOD = "{lat},{lng}/forecast";

    @GET(METHOD)
    @Headers("Accept: application/json")
    Observable<RawWeatherForecast> getWeatherData(@Path("lat") String lat, @Path("lng") String lng);
}
