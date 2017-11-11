package com.myreliablegames.troutscout;

import android.content.res.Resources;

import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.myreliablegames.troutscout.API.WeatherForecastAPI;
import com.myreliablegames.troutscout.API.WeatherForecastModel.RawWeatherForecast;
import com.myreliablegames.troutscout.API.WeatherForecastModel.WeatherForecast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Joe on 11/11/2017.
 */

public class ForecastUtil {

    public static Observable<List<WeatherForecast>> getForecastFromLatLng(final LatLng latLng, final Resources resources) {
        Observable<List<WeatherForecast>> observable = Observable.create(new ObservableOnSubscribe<List<WeatherForecast>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<WeatherForecast>> emitter) throws Exception {
                final List<WeatherForecast> forecasts = new ArrayList<>();
                DisposableObserver<RawWeatherForecast> observer = new DisposableObserver<RawWeatherForecast>() {
                    @Override
                    public void onNext(RawWeatherForecast rawWeatherForecast) {
                        forecasts.addAll(rawWeatherForecast.getProperties().getWeatherForecasts());
                        emitter.onNext(forecasts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }
                };
                getRawForecastFromLatLng(latLng, resources).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
            }
        });

        return observable;
    }

    private static Observable<RawWeatherForecast> getRawForecastFromLatLng(LatLng latLng, Resources resources) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(resources.getString(R.string.BASE_WEATHER_URL))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        WeatherForecastAPI weatherForecastAPI = retrofit.create(WeatherForecastAPI.class);
        return weatherForecastAPI.getWeatherData(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
    }
}
