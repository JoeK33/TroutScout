package com.myreliablegames.troutscout.Adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.API.WeatherForecastModel.WeatherForecast;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.databinding.WeatherItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/11/2017.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherForecast> forecasts;

    public WeatherAdapter(List<WeatherForecast> forecasts) {
        this.forecasts = forecasts;
    }

    public WeatherAdapter() {
        this.forecasts = new ArrayList<>();
    }

    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        WeatherItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.weather_item, parent, false);
        return new WeatherAdapter.ViewHolder(binding);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final WeatherItemBinding binding;

        public ViewHolder(WeatherItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(WeatherForecast weatherForecast) {
            binding.setWeatherForecast(weatherForecast);
            binding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.ViewHolder holder, int position) {
        final WeatherForecast forecast = forecasts.get(position);
        holder.bind(forecast);
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    public void replaceItems(List<WeatherForecast> items) {
        forecasts.clear();
        forecasts.addAll(items);
    }
}
