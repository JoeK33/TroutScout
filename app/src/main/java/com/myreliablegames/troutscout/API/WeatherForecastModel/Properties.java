package com.myreliablegames.troutscout.API.WeatherForecastModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Joe on 11/11/2017.
 */

public class Properties {

    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("units")
    @Expose
    private String units;
    @SerializedName("forecastGenerator")
    @Expose
    private String forecastGenerator;
    @SerializedName("generatedAt")
    @Expose
    private String generatedAt;
    @SerializedName("periods")
    @Expose
    private List<WeatherForecast> weatherForecasts = null;

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getForecastGenerator() {
        return forecastGenerator;
    }

    public void setForecastGenerator(String forecastGenerator) {
        this.forecastGenerator = forecastGenerator;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }

    public void setWeatherForecasts(List<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }
}
