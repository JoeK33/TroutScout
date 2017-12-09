package com.myreliablegames.troutscout.Fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.myreliablegames.troutscout.API.WeatherForecastModel.WeatherForecast;
import com.myreliablegames.troutscout.Adapters.WeatherAdapter;
import com.myreliablegames.troutscout.ForecastUtil;
import com.myreliablegames.troutscout.Logger;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.FragmentWeatherBinding;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Joe on 11/11/2017.
 */

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;
    private WeatherAdapter adapter;
    private LatLng latLng;

    public static WeatherFragment newInstance() {
        WeatherFragment fragment = new WeatherFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeatherAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerview.setAdapter(adapter);
        binding.weatherTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.recyclerview.getVisibility() == View.VISIBLE) {
                    binding.recyclerview.setVisibility(View.GONE);
                } else if (binding.recyclerview.getVisibility() == View.GONE) {
                    binding.recyclerview.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }

    public void changeLatLngAndUpdateViews(LatLng latLng) {
        this.latLng = latLng;
        ForecastUtil.getForecastFromLatLng(latLng, getResources()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<List<WeatherForecast>>() {
            @Override
            public void onNext(List<WeatherForecast> weatherForecasts) {
                adapter.replaceItems(weatherForecasts);
                adapter.notifyDataSetChanged();
                Logger.e("Weather Loaded");
                Logger.e("Weather size: " + String.valueOf(weatherForecasts.size()));
            }

            @Override
            public void onError(Throwable e) {
                Logger.e("Error");
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
