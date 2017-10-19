package com.myreliablegames.troutscout;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.orm.SugarRecord;

import java.util.List;

public class StockingEvent extends SugarRecord<StockingEvent> implements Comparable<StockingEvent>  {

    private String name;
    private String county;
    private String stockDate;
    private String fishSpecies;
    private String numberOfFishStocked;
    private String fishPerPound;
    private String hatchery;
    private String notes;

    public StockingEvent() {
    }

    public StockingEvent(@NonNull  @Size(min=7, max=7) List<String> data) {
        this(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6));
    }

    public StockingEvent(String name, String stockDate, String fishSpecies, String numberOfFishStocked, String fishPerPound, String hatchery, String notes) {
        cutAndAssignName(name);
        this.stockDate = stockDate;
        this.fishSpecies = fishSpecies;
        this.numberOfFishStocked = numberOfFishStocked;
        this.fishPerPound = fishPerPound;
        cutAndAssignHatcheryName(hatchery);
        this.notes = notes;
    }

    private void cutAndAssignName(String name) {
        /* Cut the name and county apart.  Name and county are delimited by a 4 letter county code in parentheses e.g. (CLAR) which is ignored.
        If no county code exists, the county field is made blank.
        */
        if (name.contains("(")) {
            String lakeName = name.substring(0, name.indexOf("(")).trim();
            String lakeCounty = name.substring(name.indexOf(")") + 1).trim();
            this.name = lakeName;
            this.county = lakeCounty;
        } else {
            this.name = null;
            return;
        }

        // Remove lake and pond abbreviations if they exist.
        if (this.name.endsWith(" LK")) {
            this.name = this.name.replace(" LK", " LAKE");
        }

        if (this.name.startsWith("LK ")) {
            this.name = this.name.replace("LK ", "LAKE ");
        }

        if (this.name.startsWith("W ")) {
            this.name = this.name.replace("W ", "WEST ");
        }

        if (this.name.endsWith(" PD")) {
            this.name = this.name.replace(" PD", " POND");
        }

        if (this.name.endsWith(" LTL")) {
            this.name = this.name.replace(" LTL", " LITTLE");
        }

        if (this.name.endsWith(" RES")) {
            this.name = this.name.replace(" RES", " RESERVOIR");
        }

        if (this.name.contains(" ISL ")) {
            this.name = this.name.replace(" ISL ", " ISLAND ");
        }

        if (this.name.contains(" PRK ")) {
            this.name = this.name.replace(" PRK ", " PARK ");
        }

        if (name.contains(" CR ")) {
            this.name = this.name.replace(" CR ", " CREEK ");
        }
    }

    private void cutAndAssignHatcheryName(String name) {
        // Remove creek abbreviations if they exist.
        if (name.contains(" CR ")) {
            name = name.replace(" CR ", " CREEK ");
        }
        this.hatchery = name;
    }

    @Override
    public String toString() {
        return name + "\n" +
                "Stocked on: " + stockDate + " with " + numberOfFishStocked + " " + fishSpecies + "." + "\n" +
                "Fish per pound: " + fishPerPound + "\n" + " Hatchery: " + hatchery + "\n" +
                "Notes: " + notes;
    }

    @Override
    public int compareTo(@NonNull StockingEvent another) {
        return (this.name.compareTo(another.name));
    }

    public String getName() {
        return name;
    }

    public String getStockDate() {
        return stockDate;
    }

    public String getCounty() {
        return county;
    }

    public String getFishSpecies() {
        return fishSpecies;
    }

    public String getNumberOfFishStocked() {
        return numberOfFishStocked;
    }

    public String getFishPerPound() {
        return fishPerPound;
    }

    public String getHatchery() {
        return hatchery;
    }

    public String getNotes() {
        return notes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public void setFishSpecies(String fishSpecies) {
        this.fishSpecies = fishSpecies;
    }

    public void setNumberOfFishStocked(String numberOfFishStocked) {
        this.numberOfFishStocked = numberOfFishStocked;
    }

    public void setFishPerPound(String fishPerPound) {
        this.fishPerPound = fishPerPound;
    }

    public void setHatchery(String hatchery) {
        this.fishPerPound = fishPerPound;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}