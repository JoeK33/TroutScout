package com.myreliablegames.troutscout;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 11/5/2017.
 */

public class FavoritesUtil {

    private static FavoritesChangeListener listener;

    public static boolean isFavorited(LakeStockingHistory lake) {
        FavoritedLake favoritedLake = new FavoritedLake(lake.getLakeName(), lake.getCounty());

        FavoritedLake savedLake = Select.from(FavoritedLake.class).where(
                Condition.prop("name").eq(favoritedLake.getName()),
                Condition.prop("county").eq(favoritedLake.getCounty())).first();

        if (savedLake != null) {
            return true;
        }
        return false;
    }

    public static boolean addLakeToFavorites(LakeStockingHistory lake) {
        Logger.i("Add favorite");
        FavoritedLake favoritedLake = new FavoritedLake(lake.getLakeName(), lake.getCounty());

        FavoritedLake savedLake = Select.from(FavoritedLake.class).where(
                Condition.prop("name").eq(favoritedLake.getName()),
                Condition.prop("county").eq(favoritedLake.getCounty())).first();

        if (savedLake == null) {
            favoritedLake.save();
            listener.onFavoritesChanged();
            return true;
        }
        return false;
    }

    public static boolean removeLakeFromFavorites(LakeStockingHistory lake) {
        Logger.i("Remove favorite");
        FavoritedLake favoritedLake = new FavoritedLake(lake.getLakeName(), lake.getCounty());

        FavoritedLake savedLake = Select.from(FavoritedLake.class).where(
                Condition.prop("name").eq(favoritedLake.getName()),
                Condition.prop("county").eq(favoritedLake.getCounty())).first();

        if (savedLake != null) {
            savedLake.delete();
            listener.onFavoritesChanged();
            return true;
        }
        return false;
    }

    public static List<FavoritedLake> getFavoriteLakes() {
        List<FavoritedLake> favoriteLakes = new ArrayList<>();

        favoriteLakes.addAll(Select.from(FavoritedLake.class).list());

        return favoriteLakes;
    }

    public static void setListener(FavoritesChangeListener newListener) {
        listener = newListener;
    }

    public interface FavoritesChangeListener {
        void onFavoritesChanged();
    }
}
