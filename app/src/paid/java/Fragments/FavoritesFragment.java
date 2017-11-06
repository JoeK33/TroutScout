package Fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myreliablegames.troutscout.Adapters.FavoritesAdapter;
import com.myreliablegames.troutscout.FavoritesUtil;
import com.myreliablegames.troutscout.LakeStockingHistory;
import com.myreliablegames.troutscout.Logger;
import com.myreliablegames.troutscout.R;
import com.myreliablegames.troutscout.StockingDatabaseUtil;
import com.myreliablegames.troutscout.databinding.FragmentFavoritesBinding;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Joe on 11/5/2017.
 */

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;
    private FavoritesAdapter adapter;
    private DisposableObserver observer;

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavoritesAdapter();

        FavoritesUtil.setListener(new FavoritesUtil.FavoritesChangeListener() {
            @Override
            public void onFavoritesChanged() {
                refreshFavoritesList();
            }
        });
        refreshFavoritesList();
    }

    private void refreshFavoritesList() {
        final StockingDatabaseUtil util = StockingDatabaseUtil.getInstance();
        if (observer != null) {
            observer.dispose();
        }
        observer = new DisposableObserver<List<LakeStockingHistory>>() {
            @Override
            public void onNext(@NonNull List<LakeStockingHistory> lakeStockingHistories) {
                adapter.replaceItems(lakeStockingHistories);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        util.getFavoriteLakes().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (observer != null && !observer.isDisposed()) {
            observer.dispose();
        }
    }
}
