package com.myreliablegames.troutscout;

import android.support.annotation.NonNull;
import android.support.annotation.Size;

import com.orm.SugarRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // Matcher to split name and counties into seperate fields. regex on the county code indicates the split.
        String regexCountyCode = "\\([0-9A-Z\\)]{4}\\)";
        Pattern pattern = Pattern.compile(regexCountyCode);
        Matcher matcher = pattern.matcher(name);

        // Look for the county code
        if (matcher.find()) {
            String lakeName = name.substring(0, matcher.start()).trim();
            String lakeCounty = name.substring(matcher.end() + 1).trim();
            lakeCounty = lakeCounty.substring(0, lakeCounty.indexOf("-")).trim();
            this.name = lakeName;

            String regexAnyNumber = "[0-9]";
            Pattern patternAnyNum = Pattern.compile(regexAnyNumber);
            Matcher matcherAnyNum = patternAnyNum.matcher(lakeCounty);

            // bail out if numbers sneak into the county.
            if (matcherAnyNum.find()) {
                this.name = null;
                return;
            }
            this.county = lakeCounty;
        } else {
            // Contains no county code, bail out.
            this.name = null;
            return;
        }

        // Remove lake and pond abbreviations if they exist, replace with word.
        if (this.name.endsWith(" LK")) {
            this.name = this.name.replace(" LK", " LAKE");
        }

        if (this.name.startsWith("LK ")) {
            this.name = this.name.replace("LK ", "LAKE ");
        }

        if (this.name.contains(" LK ")) {
            this.name = this.name.replace(" LK ", " LAKE ");
        }

        if (this.name.endsWith(" LKS")) {
            this.name = this.name.replace(" LKS", " LAKES");
        }

        if (this.name.startsWith("W ")) {
            this.name = this.name.replace("W ", "WEST ");
        }

        if (this.name.endsWith(" PD")) {
            this.name = this.name.replace(" PD", " POND");
        }

        if (this.name.contains(" PD ")) {
            this.name = this.name.replace(" PD ", " POND ");
        }

        if (this.name.contains(" HRBR ")) {
            this.name = this.name.replace(" HRBR ", " HARBOR ");
        }

        if (this.name.contains(" EVNT ")) {
            this.name = this.name.replace(" EVNT ", " EVENT ");
        }

        if (this.name.startsWith("LTL ")) {
            this.name = this.name.replace("LTL ", "LITTLE ");
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