package com.myreliablegames.troutscout;

import android.databinding.BindingAdapter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * Created by Joe on 10/15/2017.
 */

public class LakeStockingEvent implements Comparable<LakeStockingEvent>, Serializable {

    private LocalDate stockDate;
    private String fishSpecies;
    private int numberOfFishStocked;
    private float fishPerPound;
    private String hatchery;
    private String notes;

    public LakeStockingEvent() {
    }

    public LakeStockingEvent(StockingEvent event) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("MMM dd, yyyy");
        stockDate = fmt.parseLocalDate(event.getStockDate());
        fishSpecies = event.getFishSpecies();
        numberOfFishStocked = Integer.valueOf(event.getNumberOfFishStocked().replace(",", ""));
        fishPerPound = Float.valueOf(event.getFishPerPound());
        hatchery = event.getHatchery();
        notes = event.getNotes();
    }

    @BindingAdapter("setTextFromFloat")
    public static void setTextFromFloat(TextView textView, float value) {
        textView.setText(String.valueOf(value));
    }

    @BindingAdapter("setTextFromInt")
    public static void setTextFromFloat(TextView textView, int value) {
        textView.setText(String.valueOf(value));
    }

    public LocalDate getStockDate() {
        return stockDate;
    }

    public void setStockDate(LocalDate stockDate) {
        this.stockDate = stockDate;
    }

    public String getFishSpecies() {
        return fishSpecies;
    }

    public void setFishSpecies(String fishSpecies) {
        this.fishSpecies = fishSpecies;
    }

    public int getNumberOfFishStocked() {
        return numberOfFishStocked;
    }

    public void setNumberOfFishStocked(int numberOfFishStocked) {
        this.numberOfFishStocked = numberOfFishStocked;
    }

    public float getFishPerPound() {
        return fishPerPound;
    }

    public void setFishPerPound(int fishPerPound) {
        this.fishPerPound = fishPerPound;
    }

    public String getHatchery() {
        return hatchery;
    }

    public void setHatchery(String hatchery) {
        this.hatchery = hatchery;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int compareTo(@NonNull LakeStockingEvent o) {
        return getStockDate().compareTo(o.getStockDate());
    }
}
